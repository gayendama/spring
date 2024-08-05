package sn.sensoft.springbatch.utils

import grails.gorm.transactions.Transactional
import grails.web.context.ServletContextHolder
import org.apache.tika.detect.DefaultDetector
import org.apache.tika.detect.Detector
import org.apache.tika.metadata.Metadata
import org.apache.tika.mime.MediaType
import org.springframework.web.multipart.MultipartFile

@Transactional
class FileService {

    String detectDocTypeUsingTika(InputStream stream) throws IOException {
        Detector detector = new DefaultDetector()
        Metadata metadata = new Metadata()
        MediaType mediaType = detector.detect(stream, metadata)
        return mediaType.toString()
    }

    String extractExtension(MultipartFile file) {
        String filename = file.getOriginalFilename()
        return filename.substring(filename.lastIndexOf(".") + 1)
    }

    def createXLSFile(String fileName, byte[] obj) {
        String path = "${ServletContextHolder.servletContext.getRealPath("/")}"

        File f = new File(path + fileName)
        log.debug("File f ${f}")
        if (f.exists()) {
            f.delete()
            f.createNewFile()
        }

        FileOutputStream out = new FileOutputStream(path + fileName)
        out.write(obj)
        out.flush()
        out.close()
    }
}
