package pl.pa3c.agileman.events;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class FailedSaveFileHandler {

	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	@Async
	public void deleteSavedFileInStorage(FailedSaveFile failedSaveFile) throws IOException {
		Files.deleteIfExists(Paths.get(failedSaveFile.getFileLocation()));
	}
}
