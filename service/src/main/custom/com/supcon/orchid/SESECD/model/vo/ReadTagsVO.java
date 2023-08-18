package com.supcon.orchid.SESECD.model.vo;

import java.util.List;

/**
 * @author: xulong2
 * @create: 2021-03-30 15:55
 * @description
 **/
public class ReadTagsVO {

    private List<TagDataVO> ReadTagsSyncResult;

    public List<TagDataVO> getReadTagsSyncResult() {
        return ReadTagsSyncResult;
    }

    public void setReadTagsSyncResult(List<TagDataVO> readTagsSyncResult) {
        ReadTagsSyncResult = readTagsSyncResult;
    }
}
