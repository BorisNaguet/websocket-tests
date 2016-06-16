package dw.websocket.gatling;

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class BasicSimulation extends Simulation {

	val httpConf = http
		.baseURL("http://localhost:8080")
		.wsBaseURL("ws://localhost:8080")
		  .wsReconnect
		  .wsMaxReconnects(10)
		.acceptHeader("application/json")
		.contentTypeHeader("application/json")

	val random = new util.Random
	val tenantFeeder = Iterator.continually(Map("tenantid" -> ("tenant-" + random.nextInt())))

	val openWs = (tenant: String) => {
		val url = s"/ws-test/${tenant}"
		ws("Open Websocket", "theWs").open(url)
	}

	val closeWs = (tenant: String) => {
		val url = s"/ws-test/${tenant}"
		ws("Close Websocket", "theWs").close
	}

	val sendSms = (i : Int, tenant : String) => repeat(i, "n") {
		val url = s"/rs-test/${tenant}"
		exec(ws("send hello", "theWs").sendText("hello").check(wsListen.within(10 seconds).expect(1).jsonPath("$.mess").is("HELLO")))
		.exec(ws("send hello for HOHO", "theWs").sendText("hello").check(wsListen.within(10 seconds).until(1).regex(".*").is("HOHO")))
			exec(
				http("send_single_sms")
					.post(url)
					.header("Content-Type", "application/json")
					.body(StringBody(s"""
						{
							"text": "Message text on tenant ${tenant} n° $${n}",
							"to": "0507087870"
						}
						"""))
					.check(jsonPath("$.id").ofType[String].saveAs("MessId"))
		)
		  .exec(ws("check ACK MessId", "theWs").check(wsListen.within(110 seconds).accumulate.jsonPath("$.id").is("${MessId}")))
		  .exec(ws("check ACK is OK", "theWs").check(wsListen.within(110 seconds).accumulate.jsonPath("$.status").is("OK")))
		  .repeat(10, "j") {
				exec(ws("NOK accumulated check", "theWs").check(wsListen.within((5) seconds).accumulate.jsonPath("$.status").is("never happens")))
			}
		  .pause(1 second)
	}

	val scn = scenario("scenario 1")
		.feed(tenantFeeder)
		.exec(openWs("${tenantid}"))
		.pause(1)
		.exec(sendSms(50, "${tenantid}"))
		.pause(120)
	  .exec(closeWs("${tenantid}"))


	setUp(
    scn.inject(rampUsers(20) over(3 seconds))
  ).protocols(httpConf)
}