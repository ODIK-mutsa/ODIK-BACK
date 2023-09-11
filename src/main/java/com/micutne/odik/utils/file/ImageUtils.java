package com.micutne.odik.utils.file;

import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.common.exception.InvalidValueException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 이미지 입출력에 사용하는 Utils
 */
public class ImageUtils extends FileUtils {
    private ImageUtils() {
        super();
    }

    public static FileResponse saveFile(MultipartFile file, String category) {
        if (validImageExtension(file.getOriginalFilename())) {
            return FileUtils.saveFile(file, category);
        } else throw new InvalidValueException(ErrorCode.FILE_NOT_VALID_EXTENSION);
    }

    public static List<FileResponse> saveFiles(MultipartFile files[], String category) {
        List<FileResponse> result = new ArrayList<>();
        for (MultipartFile file : files) {
            if (validImageExtension(file.getOriginalFilename())) {
                result.add(FileUtils.saveFile(file, category));
            } else throw new InvalidValueException(ErrorCode.FILE_NOT_VALID_EXTENSION);
        }
        return result;
    }

    public static void removeFile(String filename, String category) {
        FileUtils.remove(filename, category);
    }

    /**
     * 이미지가 맞는지 확인
     */
    public static boolean validImageExtension(String originalFilename) {
        String ext = getExt(originalFilename);
        boolean check = false;
        for (String extension : Extensions.IMAGE.getExtensions()) {
            if (extension.equals(ext)) {
                check = true;
                break;
            }
        }
        return check;
    }
}
