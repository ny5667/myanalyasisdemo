package com.supcon.orchid.SESECD.model.vo;

import java.util.List;

/**
 * @ClassName AlarmPlaceAndChannelVO
 * @Description TODO
 * @Author shaochengcheng
 * @Date 2021/5/18 11:10
 */
public class CommonPlaceAndChannelVO {
    /**
     *  常用地址名称
     */

    private String commonPlaceName;
    /**
     * 常用地址Id
     */
    private Long  commonPlaceId;

    /**
     * 所属摄像头
     */
    private List<CameraVO> cameraVOList;

    public String getCommonPlaceName() {
        return commonPlaceName;
    }

    public void setCommonPlaceName(String commonPlaceName) {
        this.commonPlaceName = commonPlaceName;
    }

    public Long getCommonPlaceId() {
        return commonPlaceId;
    }

    public void setCommonPlaceId(Long commonPlaceId) {
        this.commonPlaceId = commonPlaceId;
    }

    public List<CameraVO> getCameraVOList() {
        return cameraVOList;
    }

    public void setCameraVOList(List<CameraVO> cameraVOList) {
        this.cameraVOList = cameraVOList;
    }



}
