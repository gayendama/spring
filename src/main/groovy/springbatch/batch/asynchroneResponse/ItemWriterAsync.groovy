package springbatch.batch.asynchroneResponse

import groovy.util.logging.Slf4j
import org.springframework.batch.item.ItemWriter
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.parametrage.ProgrammeBatch
import sn.sensoft.springbatch.utils.Constantes

@Slf4j
public class ItemWriterAsync implements ItemWriter<ProgrammeBatch> {

    @Override
    public void write(List<? extends ProgrammeBatch> items) throws Exception {
        if (items != null){
            items.each {ProgrammeBatch programmeBatch ->
                if(programmeBatch.programStatus == Constantes.STATUT_FAILED){
                    throw new SpringbatchException(programmeBatch.errorMessage)
                }
            }
        }
    }
}
