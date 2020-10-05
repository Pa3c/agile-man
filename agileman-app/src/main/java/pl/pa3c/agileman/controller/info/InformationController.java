package pl.pa3c.agileman.controller.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.RestController;
import pl.pa3c.agileman.api.info.InformationSI;
import pl.pa3c.agileman.api.info.InformationSO;

import java.time.Instant;

@RestController
public class InformationController implements InformationSI {

    @Autowired
    private BuildProperties buildProperties;

    @Value("${git.commit.message.short}")
    private String commitMessage;

    @Value("${git.branch}")
    private String branch;

    @Value("${git.commit.id}")
    private String commitId;
	
	@Override
	public InformationSO get() {
		InformationSO informationSO = new InformationSO();
		informationSO.setVersion(buildProperties.getVersion());
		Instant instant = buildProperties.getTime();
		if(instant != null){
			informationSO.setTime(buildProperties.getTime().toEpochMilli());
			informationSO.setGitCommitID(commitId);
			informationSO.setGitCommitBranch(branch);
			informationSO.setGitCommitMessage(commitMessage);
		}else{
			informationSO.setTime(System.currentTimeMillis());
		}
		return informationSO;
	}

}
