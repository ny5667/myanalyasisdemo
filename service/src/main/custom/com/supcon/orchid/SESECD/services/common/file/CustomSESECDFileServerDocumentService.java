package com.supcon.orchid.SESECD.services.common.file;

import com.supcon.orchid.SESECD.model.vo.common.PictureFileVO;
import com.supcon.supfusion.file.server.api.vo.DocumentVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomSESECDFileServerDocumentService {
    List<PictureFileVO> getFileServerByIconIds(List<Long> iconIds);

    PictureFileVO getFileServerByIconId(Long iconId);

    /**
     *
     * @param file 文件对象
     * @param linkId  业务主键 用于业务关联文件文件
     * @return
     */
    DocumentVO save(MultipartFile file,Long linkId);
}
