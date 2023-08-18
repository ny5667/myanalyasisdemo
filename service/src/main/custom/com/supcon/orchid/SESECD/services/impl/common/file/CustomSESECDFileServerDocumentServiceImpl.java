package com.supcon.orchid.SESECD.services.impl.common.file;

import com.supcon.orchid.SESECD.model.vo.common.PictureFileVO;
import com.supcon.orchid.SESECD.services.common.file.CustomSESECDFileServerDocumentService;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.supfusion.file.server.api.BapFileService;
import com.supcon.supfusion.file.server.api.dto.DocumentSaveDTO;
import com.supcon.supfusion.file.server.api.vo.DocumentVO;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomSESECDFileServerDocumentServiceImpl extends BaseServiceImpl implements CustomSESECDFileServerDocumentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 处理文件
     */
    @Autowired
    private BapFileService fileService;
    /**
     * 通过ids批量查询文件 FILE_SERVER_DOCUMENT
     * @param iconIds
     * @return
     */
    @Override
    public List<PictureFileVO> getFileServerByIconIds(List<Long> iconIds) {
        if (CollectionUtils.isEmpty(iconIds)) {
            return null;
        }
        //Long 转 String
        List<String> ids = iconIds.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.toList());

        StringBuilder sql = new StringBuilder();
        if (iconIds.size() > 1) {
            //获取图标库图标路径
            sql.append("SELECT id, LINK_ID as linkId ,FILE_PATH as filePath ,FILE_NAME as fileName FROM FILE_SERVER_DOCUMENT fsd WHERE valid = 1 and LINK_ID IN (").append(String.join(",", ids)).append(")");
        } else {
            sql.append("SELECT id, LINK_ID as linkId ,FILE_PATH as filePath ,FILE_NAME as fileName FROM FILE_SERVER_DOCUMENT fsd WHERE valid = 1 and LINK_ID = ").append(iconIds.get(0));
        }
        log.error("getFileServerByIconIds========查询sql:{}", sql.toString());
        List<PictureFileVO> pictureFileVos = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(PictureFileVO.class));
        log.error("getFileServerByIconIds========图标路径 数量:{}", pictureFileVos.size());
        return pictureFileVos;
    }

    /**
     * 通过id批量查询文件 FILE_SERVER_DOCUMENT
     * @param iconId
     * @return
     */
    @Override
    public PictureFileVO getFileServerByIconId(Long iconId) {
        if (null == iconId) {
            return null;
        }
        //获取图标库图标路径
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, LINK_ID as linkId ,FILE_PATH as filePath ,FILE_NAME as fileName FROM FILE_SERVER_DOCUMENT fsd WHERE valid = 1 and LINK_ID  = ").append(iconId);
        log.error("getFileServerByIconId======查询sql:{}", sql.toString());
        PictureFileVO pictureFileVo = jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(PictureFileVO.class));
        return pictureFileVo;
    }

    @Override
    public DocumentVO save(MultipartFile file, Long linkId) {
        Result result = fileService.fileUpload(file);
        if(StringUtils.isEmpty(result.getData().toString())){
                return null;
        }
        String path = result.getData().toString();
        DocumentSaveDTO dto = new DocumentSaveDTO();
        Long[] linkIds = new Long[]{linkId};
        String[] filePaths = new String[]{path};
        String[] fileTypes = new String[]{"pic"};
        dto.setLinkIds(linkIds);
        dto.setFilePaths(filePaths);
        dto.setFileTypes(fileTypes);
        List<DocumentVO> documentVOS = fileService.moveFilesJson(dto);
        if (CollectionUtils.isNotEmpty(documentVOS)){
            return documentVOS.get(0);
        }

        return null;
    }
}
