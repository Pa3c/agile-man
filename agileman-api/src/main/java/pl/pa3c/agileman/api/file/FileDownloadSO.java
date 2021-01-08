package pl.pa3c.agileman.api.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadSO {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
