package sn.sensoft.springbatch.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken

import static io.micronaut.http.HttpHeaders.*

@Client("\${auth.url}")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = MediaType.APPLICATION_JSON)
@Header(name = CONTENT_TYPE, value = MediaType.APPLICATION_FORM_URLENCODED)
interface KeycloakClient {
    @Post(uri = "\${auth.loginUri}", produces = "application/json")
    HttpResponse<BearerAccessRefreshToken> login(@Body Map<String, String> body);
}