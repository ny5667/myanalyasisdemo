//package com.supcon.orchid.SESECD.services;
//
//import com.supcon.orchid.SESEAB.DTO.MemberDTO;
//import com.supcon.orchid.SESECD.model.vo.ConversationVO;
//import com.supcon.orchid.SESECD.model.vo.MemberVO;
//import com.supcon.orchid.SESECD.entities.SESECDSignalConfig;
//import com.supcon.orchid.support.Result;
//
//import java.util.List;
//
///**
// * @author: xulong2
// * @create: 2021-03-18 15:01
// * @description
// **/
//public interface CustomSESECDCommunicationService {
//
//    /**
//     * 融合通信号码呼叫或挂断
//     * @param vo
//     * @return
//     */
//    Result<String> conversation(ConversationVO vo);
//
//    /**
//     * 获取应急小组的在线状态
//     * @param staffIdsByPlanIds
//     * @return
//     */
//    List<MemberVO> getPersonOnlineStatus(List<MemberDTO> staffIdsByPlanIds);
//
//
//    /**
//     * 获取外部接口配置
//     * @param type
//     * @return
//     */
//    SESECDSignalConfig getSignalConfig(String type,long cid);
//}
