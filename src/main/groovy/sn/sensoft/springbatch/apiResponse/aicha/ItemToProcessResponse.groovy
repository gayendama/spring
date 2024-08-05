package sn.sensoft.springbatch.apiResponse.aicha

import groovy.transform.CompileStatic
import sn.sensoft.springbatch.ItemToProcess

@CompileStatic
class ItemToProcessResponse {
    Integer count
    List<ItemToProcess> data
}
