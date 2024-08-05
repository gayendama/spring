package sn.sensoft.springbatch

import org.apache.commons.lang.time.DurationFormatUtils

class DurationTagLib {
	static namespace = 'batch'
	
	def durationPrint = { attrs, body ->
		
		if(!attrs.duration) {
			String errorMsg = 'Duration attribute is required for batch:durationPrint tag'
			log.debug (errorMsg)
			return
		}
		
		Long duration = 0L
		if((attrs.duration instanceof String) || (attrs.duration instanceof GString)){
			if(attrs.duration.isLong()) {
				duration = attrs.duration.toLong()
			}else {
				log.debug("Duration ${attrs.duration} must be a Long")
			}
		}else if(attrs.duration instanceof Number){
			duration = attrs.duration.toLong()
		}else {
			log.debug("Duration ${attrs.duration} must be a long")
		}
		
		String format = 'HH:mm:ss.S'
		if(attrs.format) {
			format = attrs.format
		}
		
		out << DurationFormatUtils.formatDuration(duration, format, false)
	}
	
}
