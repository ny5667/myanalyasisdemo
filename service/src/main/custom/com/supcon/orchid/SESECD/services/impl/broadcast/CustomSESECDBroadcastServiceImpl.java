package com.supcon.orchid.SESECD.services.impl.broadcast;

import com.supcon.orchid.SESECD.model.vo.broadcast.*;
import com.supcon.orchid.SESECD.daos.SESECDBroadcastInfoDao;
import com.supcon.orchid.SESECD.services.broadcast.CustomSESECDBroadcastService;
import com.supcon.orchid.SESECD.strategy.AbstratcStrategy.BroadcastStrategy;
import com.supcon.orchid.SESECD.strategy.BroadcastFactory;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 接入第三方广播设备
 */
@Service
@Transactional
public class CustomSESECDBroadcastServiceImpl extends BaseServiceImpl implements CustomSESECDBroadcastService {

    @Autowired
    SESECDBroadcastInfoDao broadcastInfoDao;


    @Override
    public BroadcastPageResult getBroadcastInfoList(BroadcastPageVO pageVO) {
        log.error("开启广播");
        String companyType = this.getCompanyType();
        BroadcastStrategy strategy = BroadcastFactory.createStrategy(companyType);
        List<BroadcastInfoDTO> data = strategy.fetchAudioChannel(pageVO);
        BroadcastPageResult result = new BroadcastPageResult();
        result.setData(data);
        result.setTotal(data.size());
        result.setPageNo(1);
        return result;
    }

    /**
     * 用海康获取H5实时广播URL接口
     * @return
     */
    @Override
    public BroadcastH5URLResponse broadcastURLs() {
        log.error("进入获取获取H5实施广播接口" );
        String companyType = this.getCompanyType();
        BroadcastStrategy strategy = BroadcastFactory.createStrategy(companyType);
        BroadcastH5URLResponse response = strategy.broadcastURLs();
        log.error("获取H5实施广播URL ：" + response);
        return response;
    }

    /**
     * 开启广播
     * @param startBroadcastVO
     */
    @Override
    public void startBroadcast(StartBroadcastVO startBroadcastVO) {
        log.error("开启广播");
        String companyType = this.getCompanyType();
        BroadcastStrategy strategy = BroadcastFactory.createStrategy(companyType);
        strategy.startBroadcast(startBroadcastVO);

    }

    /**
     * 关闭广播
     * @return
     */
    @Override
    public void stopBroadcast() {
        log.error("开启广播");
        String companyType = this.getCompanyType();
        BroadcastStrategy strategy = BroadcastFactory.createStrategy(companyType);
        strategy.stopBroadcast();
    }

    /**
     * 设置广播音量
     * @param broadcastVolumeVO
     */
    @Override
    public void setAudioVolumeParam(SetBroadcastVolumeVO broadcastVolumeVO) {
        log.error("设置广播音量");
        String companyType = this.getCompanyType();
        BroadcastStrategy strategy = BroadcastFactory.createStrategy(companyType);
        strategy.setAudioVolumeParam(broadcastVolumeVO);
    }



    //TODO 后续接入其他公司可从此处扩展
    private String getCompanyType() {
        return "Hikvision";
    }
}
