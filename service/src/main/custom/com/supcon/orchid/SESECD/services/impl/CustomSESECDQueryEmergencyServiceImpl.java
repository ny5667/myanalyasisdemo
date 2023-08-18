//package com.supcon.orchid.SESECD.services.impl;
//
//
//import com.supcon.orchid.SESEAB.entities.SESEABEabSetion;
//import com.supcon.orchid.SESEAB.entities.SESEABSetionmember;
//import com.supcon.orchid.SESECD.daos.SESECDAllEmerMemberDao;
//import com.supcon.orchid.SESECD.daos.SESECDCctvRecordDao;
//import com.supcon.orchid.SESECD.services.CustomSESECDQueryEmergencyService;
//import com.supcon.orchid.SESECD.utils.SqlUtils;
//import com.supcon.orchid.SESECD.model.vo.emergencyplan.AllEmerMemberVO;
//import com.supcon.orchid.SESECD.model.vo.emergencyplan.DetparentmentVO;
//import com.supcon.orchid.services.BaseServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.TreeSet;
//import java.util.stream.Collectors;
//
///**
// * @version video 6.0
// * @name
// * @author： luoheng
// * @date： 2021-03-20 15:49
// */
//@Service
//@Transactional
//public class CustomSESECDQueryEmergencyServiceImpl extends BaseServiceImpl<SESEABEabSetion> implements CustomSESECDQueryEmergencyService {
//
//
//    @Autowired
//    private SESECDCctvRecordDao sesecdCctvRecordDao;
//
//    @Autowired
//    private SESECDAllEmerMemberDao sesecdAllEmerMemberDao;
//    @Autowired
//    private SqlUtils sqlUtils;
//
//
//    @Override
//    public List<AllEmerMemberVO> queryEmergency() {
//
//        List<AllEmerMemberVO> allEmerMemberVOList = new ArrayList<>();
//        List<DetparentmentVO> stringList = new ArrayList<>();
//        //部门   selectALL
//        //父节点  -1    子节点   部门ID    0   1
//        List<SESEABEabSetion> eabSetionList2 = sesecdCctvRecordDao.findByHql("from " +  SESEABEabSetion.JPA_NAME + " where valid = 1  " + sqlUtils.getSqlPartByCID());
//        if (null!=eabSetionList2 && eabSetionList2.size()>0){
//            for (SESEABEabSetion seseabEabSetion : eabSetionList2){
//                DetparentmentVO detparentmentVO = new DetparentmentVO();
//                Long id = seseabEabSetion.getBelongDepartment().getId();
//                String belongDepartmentName = seseabEabSetion.getBelongDepartmentName();
//                detparentmentVO.setLayRec(id);
//                detparentmentVO.setBelongDepartment(belongDepartmentName);
//                stringList.add(detparentmentVO);
//
//            }
//
//        }
////        List<DetparentmentVO> collect = stringList.stream().distinct().collect(Collectors.toList());
//        List<DetparentmentVO> collect = stringList.stream()
//                .collect(
//                        Collectors.collectingAndThen(
//                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(DetparentmentVO::getLayRec))), ArrayList::new)
//                );
//        log.info("得到部门数量"+collect.size());
//
//        for (DetparentmentVO detparentmentVO : collect){
//                AllEmerMemberVO allEmerMemberVO = new AllEmerMemberVO();
//                allEmerMemberVO.setBelongDepartment(detparentmentVO.getBelongDepartment());
//                allEmerMemberVO.setLeaf(true);
//                allEmerMemberVO.setParentId("-1");
//                allEmerMemberVO.setLayNo(1);
//                allEmerMemberVO.setLayRec(detparentmentVO.getLayRec()+"");
//                allEmerMemberVOList.add(allEmerMemberVO);
//
//
//
//
//        }
//
//
//        //查通讯组
//        //  父节点     部门id         本身id   1   2
//        List<SESEABEabSetion> eabSetionList = sesecdCctvRecordDao.findByHql("from " +  SESEABEabSetion.JPA_NAME + " where valid = 1 " + sqlUtils.getSqlPartByCID());
//        log.info("查询通讯组数据库"+eabSetionList2.size());
//        String sesecdLayRec = "";
//        if (null!=eabSetionList && eabSetionList.size()>0){
//            for (SESEABEabSetion seseabEabSetion : eabSetionList){
//                AllEmerMemberVO allEmerMemberVO = new AllEmerMemberVO();
//                allEmerMemberVO.setSectionName(seseabEabSetion.getSectionName());
//                allEmerMemberVO.setLeaf(true);
//                allEmerMemberVO.setParentId(seseabEabSetion.getBelongDepartment().getId()+"");
//                allEmerMemberVO.setLayNo(2);
//                //allEmerMemberVO.setLayRec(seseabEabSetion.getBelongDepartment().getId()+"-"+seseabEabSetion.getId());
//                allEmerMemberVO.setLayRec(seseabEabSetion.getId()+"");
//                allEmerMemberVOList.add(allEmerMemberVO);
//
//
//
//
//
//
//                log.info("插入通讯组数据条数"+allEmerMemberVOList.size());
//            }
//
//        }
//
//        //查人员表
//        //父节点   组的id     子节点   本身id    1   3
//        List< SESEABSetionmember> seseabSetionmemberList = sesecdCctvRecordDao.findByHql("from " +  SESEABSetionmember.JPA_NAME + " where valid = 1 " + sqlUtils.getSqlPartByCID());
//        log.info("查询人员表数量"+seseabSetionmemberList.size());
//        if (null!=seseabSetionmemberList && seseabSetionmemberList.size() >0){
//            for (SESEABSetionmember seseabSetionmember : seseabSetionmemberList){
//                AllEmerMemberVO allEmerMemberVO = new AllEmerMemberVO();
//                allEmerMemberVO.setPersonId(seseabSetionmember.getPersonId().getId());
//                allEmerMemberVO.setStaffCode(seseabSetionmember.getPersonId().getCode());
//                allEmerMemberVO.setPersonName(seseabSetionmember.getPersonName());
//                allEmerMemberVO.setMobile(seseabSetionmember.getMobile());
//                allEmerMemberVO.setTelephone(seseabSetionmember.getTelephone());
//                allEmerMemberVO.setLeaf(false);
//                if(seseabSetionmember.getSetionId() !=null){
//                    allEmerMemberVO.setParentId(seseabSetionmember.getSetionId().getId().toString());
//                }                allEmerMemberVO.setLayNo(3);
//                //allEmerMemberVO.setLayRec(seseabSetionmember.getSetionId().getBelongDepartment().getId()+"-"+seseabSetionmember.getSetionId().getId()+"-"+seseabSetionmember.getId());
//                allEmerMemberVO.setLayRec(seseabSetionmember.getId()+"");
//                allEmerMemberVOList.add(allEmerMemberVO);
//                log.info("插入人员信息数据条数"+allEmerMemberVOList.size());
//
//
//
//
//            }
//
//        }
//        log.info("一共插入数据调试"+allEmerMemberVOList.size());
//        return allEmerMemberVOList;
//    }
//}
