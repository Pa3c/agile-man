package pl.pa3c.agileman.api.info;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data


public class InformationSO {
    private String version;
    private Long time;
    private String gitCommitID;
    private String gitCommitBranch;
    private String gitCommitMessage;
}
