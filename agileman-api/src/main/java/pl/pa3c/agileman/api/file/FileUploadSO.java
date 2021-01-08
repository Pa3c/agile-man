package pl.pa3c.agileman.api.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadSO {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private String targetPath;
    private long size;
}
