package springbatch.batch.asynchroneResponse

import groovy.util.logging.Slf4j
import org.springframework.batch.item.ItemProcessor
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.parametrage.ProgrammeBatch
import sn.sensoft.springbatch.utils.Constantes

@Slf4j
public class ItemProcessorAsync implements ItemProcessor<ProgrammeBatch, ProgrammeBatch>{

    static boolean cleared = false
    String objet

    @Override
    public ProgrammeBatch process(ProgrammeBatch programmeBatch) throws Exception {

        if (!cleared) {
            cleared = true
        }

        //Need to do this because the HibernateCursorItemReader doesn't attach
        //the instance of the session
//        if (!programmeBatch.isAttached()) {
//            programmeBatch.attach()
//        }

        //Vérifier si le backup est toujours en cours. Si oui retourner si non continuer
        if(programmeBatch.programStatus == Constantes.STATUT_STARTED){
            Thread.sleep(60000)
            return
        }

        programmeBatch
    }
}
