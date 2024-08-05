package sn.sensoft.springbatch.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import sn.sensoft.springbatch.apiRequest.aicha.ItemsToProcessRequest
import sn.sensoft.springbatch.apiRequest.aicha.ProcessRequest
import sn.sensoft.springbatch.apiResponse.aicha.*
import sn.sensoft.springbatch.dto.CompteDTO

import static io.micronaut.http.HttpHeaders.*

@Client("\${aicha.url}")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = "application/json")
interface AichaClient {
    @Get(uri = "/agency-situation", produces = "application/json")
    HttpResponse<SituationAgenceResponse> getAgencySituation(@Header(name = "Authorization") String authorization)

    @Get(uri = "/start-or-stop-batch?status={status}", produces = "application/json")
    HttpResponse<SimpleResponse> startOrStopBatch(@Header(name = "Authorization") String authorization, String status)

    @Post(uri = "/items-to-precess", produces = "application/json")
    HttpResponse<ItemToProcessResponse> getItemsToPrecess(@Header(name = "Authorization") String authorization,  @Body ItemsToProcessRequest body)

    @Post(uri = "/process-item", produces = "application/json")
    @Header(name = CONTENT_TYPE, value = MediaType.APPLICATION_JSON)
    HttpResponse<SimpleResponse> processItem(@Header(name = "Authorization") String authorization, @Body ProcessRequest body)

    @Post(uri = "/items-to-process-reprise", produces = "application/json")
    HttpResponse<ItemToProcessResponse> getItemsToPrecessReprise(@Header(name = "Authorization") String authorization,  @Body ItemsToProcessRequest body)

    @Post(uri = "/process-item-reprise", produces = "application/json")
    @Header(name = CONTENT_TYPE, value = MediaType.APPLICATION_JSON)
    HttpResponse<SimpleResponse> processItemReprise(@Header(name = "Authorization") String authorization, @Body ProcessRequest body)

    @Get(uri = "/updates-after-step?batch={batch}&program={program}", produces = "application/json")
    HttpResponse<SimpleResponse> updatesAfterStep(@Header(name = "Authorization") String authorization, String batch, String program)

    @Get(uri = "/date-arretes", produces = "application/json")
    HttpResponse<DateArreteResponse> dateArretes(@Header(name = "Authorization") String authorization)

    @Get(uri = "/branches", produces = "application/json")
    HttpResponse<BureauResponse> branches(@Header(name = "Authorization") String authorization)

    @Get(uri = "/search-account?branchCode={branchCode}&keyword={keyword}", produces = "application/json")
    HttpResponse<List<CompteDTO>> searchAccount(@Header(name = "Authorization") String authorization, String branchCode, String keyword)


}