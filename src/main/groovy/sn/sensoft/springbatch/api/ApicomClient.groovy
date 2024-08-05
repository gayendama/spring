package sn.sensoft.springbatch.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import org.grails.web.json.JSONObject

import static io.micronaut.http.HttpHeaders.ACCEPT
import static io.micronaut.http.HttpHeaders.USER_AGENT

@Client("\${apicom.url}")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = "application/json")
interface ApicomClient {

    @Post(uri = "\${apicom.context}/api/login", produces = "application/json")
    HttpResponse<BearerAccessRefreshToken> login(@Body String body);

    @Post(uri = "\${apicom.context}/api/v2/sendsmsmail", produces = "application/json")
    @Header(name = USER_AGENT, value = "Micronaut HTTP Client")
    HttpResponse<JSONObject> sendSmsMail(@Header(name = "Authorization") String authorization, @Body String body);

}