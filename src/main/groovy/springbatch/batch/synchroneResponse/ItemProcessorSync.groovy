package springbatch.batch.synchroneResponse


import groovy.util.logging.Slf4j
import org.springframework.batch.item.ItemProcessor
import sn.sensoft.springbatch.ItemToProcess
import sn.sensoft.springbatch.ToolsBatchService

@Slf4j
public class ItemProcessorSync implements ItemProcessor<ItemToProcess, ItemToProcess> {

    static boolean cleared = false
    def ToolsBatchService toolsBatchService

    @Override
    public ItemToProcess process(ItemToProcess itemToProcess) throws Exception {

        if (!cleared) {
            cleared = true
        }

        //Need to do this because the HibernateCursorItemReader doesn't attach
        //the instance of the session
        if (!itemToProcess.isAttached()) {
            itemToProcess.attach()
        }

        //exclure les lignes déjà traitées s'il sagit d'un restart
        if (toolsBatchService.isAlreadyDone(itemToProcess.objetId)) {
            return
        }

        itemToProcess
    }
}
