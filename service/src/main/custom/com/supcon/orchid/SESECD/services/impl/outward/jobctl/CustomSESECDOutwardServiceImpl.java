package com.supcon.orchid.SESECD.services.impl.outward.jobctl;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESECD.model.dto.outward.jobctl.HuiZhouWorkResponseDTO;
import com.supcon.orchid.SESECD.model.dto.outward.jobctl.JobControlDTO;
import com.supcon.orchid.SESECD.model.vo.outward.jobctl.JobControlPersonVO;
import com.supcon.orchid.SESECD.model.vo.outward.jobctl.JobControlVO;
import com.supcon.orchid.SESECD.services.outward.jobctl.CustomSESECDOutwardService;
import com.supcon.orchid.SESECD.utils.HttpInvokeUtils;
import com.supcon.orchid.SesCommonFunc.util.FieldType;
import com.supcon.orchid.SesCommonFunc.util.GISUtils;
import com.supcon.orchid.SesCommonFunc.vo.BaseInfoVo;
import com.supcon.orchid.SesCommonFunc.vo.BaseShellVo;
import com.supcon.orchid.SesCommonFunc.vo.HeadVo;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomSESECDOutwardServiceImpl extends BaseServiceImpl implements CustomSESECDOutwardService {
    // FIXME 这里放事务没什么用，没有和数据库有交互， 也不用继承BaseServiceImpl
    public static final String jobControlUrl = "http://10.152.70.4:8040/cpo/PosRfld/selectUserIdByUser";

    /**
     * 作业受控
     *
     * @param jobControlDTO
     * @return
     */
    @Override
    public BaseInfoVo getJobControl(JobControlDTO jobControlDTO) {
        BaseShellVo baseShellVo = new BaseShellVo();
        List<HeadVo> headVos = getHeadVos();
        baseShellVo.setHead(headVos);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = getHttpEntity(jobControlDTO);
        List<JobControlVO> responses;
        String resultStr;
        //请求第三方接口
        try{
            resultStr = HttpInvokeUtils.invoke(jobControlUrl, HttpMethod.POST, httpEntity, String.class);
            System.err.println("作业受控请求结果：" + resultStr);
            // FIXME: 没有将状态值变更为中文
            responses = JSON.parseArray(resultStr, JobControlVO.class);
        }catch (Exception var){
            log.error("请求第三方作业受控接口失败，原因：{0}", var);
            responses = new ArrayList<>();
        }
        responses = JSON.parseArray(response, JobControlVO.class);
        //作业状态转换
        for (JobControlVO respons : responses) {
            String curStatusName = getCurStatusName(Integer.parseInt(respons.getCurstatus()));
            respons.setCurstatus(curStatusName);
        }
        baseShellVo.setData(responses);
        return BaseInfoVo.ok(baseShellVo);
        /* //responses = JSON.parseArray(response, HuiZhouWorkResponse.class);

        //建立作业受控 和人员映射
        Map<BigDecimal, List<JobControlPersonVO>> jobControlPersonMapping = getJobControlPersonMapping(responses);
        //根据OperationId去重
        List<HuiZhouWorkResponse> responseArrayList = responses.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(HuiZhouWorkResponse::getOperationId))), ArrayList::new)
        );
        //封装
        List<JobControlVO> jobControlVOList = new ArrayList<>();
        for (HuiZhouWorkResponse response : responseArrayList) {
            JobControlVO jobControlVO = getJobControlVO(jobControlPersonMapping, response);
            jobControlVOList.add(jobControlVO);
        }
        return jobControlVOList;*/

    }

    private static List<HeadVo> getHeadVos() {
        List<HeadVo> headVos = new ArrayList<>();
        GISUtils.orgData(headVos, "operationId", FieldType.STRING.getType(), "id");// ID
        GISUtils.orgData(headVos, "operLocation", FieldType.STRING.getType(), "作业地点名称");
        GISUtils.orgData(headVos, "operContent", FieldType.STRING.getType(), "作业内容");
        GISUtils.orgData(headVos, "curstatus", FieldType.STRING.getType(), "作业状态");// 行动地
        GISUtils.orgData(headVos, "px", FieldType.STRING.getType(), "经度");
        GISUtils.orgData(headVos, "py", FieldType.STRING.getType(), "维度");
        GISUtils.orgData(headVos, "jl", FieldType.STRING.getType(), "坐标距离");
        GISUtils.orgData(headVos, "ulist", FieldType.STRING.getType(), "作业人员");
        GISUtils.orgData(headVos, "uname", FieldType.STRING.getType(), "员工名称");
        GISUtils.orgData(headVos, "usex", FieldType.STRING.getType(), "员工性别");
        GISUtils.orgData(headVos, "uemail", FieldType.STRING.getType(), "员工邮箱");
        GISUtils.orgData(headVos, "umobile", FieldType.STRING.getType(), "员工电话");
        return headVos;
    }

    private static String getCurStatusName(int curstatus) {
        String curStatusName = "";
        switch (curstatus) {
            case -4:
                curStatusName = "取消预约";
                break;
            case -2:
                curStatusName = "暂存";
                break;
            case -1:
                curStatusName = "保存";
                break;
            case 0:
                curStatusName = "计划中";
                break;
            case 1:
                curStatusName = "申请";
                break;
            case 2:
                curStatusName = "审批流转中";
                break;
            case 5:
                curStatusName = "完成审批";
                break;
            case 6:
                curStatusName = "审核不通过";
                break;
            case 7:
                curStatusName = "已暂停";
                break;
            case 8:
                curStatusName = "已取消";
                break;
            case 10:
                curStatusName = "已关闭";
                break;
            case 11:
                curStatusName = "后续日未确认";
                break;
            default:
                curStatusName = "其他";
        }
        return curStatusName;
    }

    private static JobControlVO getJobControlVO(Map<BigDecimal, List<JobControlPersonVO>> jobControlPersonMapping, HuiZhouWorkResponseDTO response) {
        JobControlVO jobControlVO = new JobControlVO();
        jobControlVO.setOperationId(response.getOperationId());
        jobControlVO.setOperLocation(response.getOperLocation());
        jobControlVO.setOperContent(response.getOperContent());
        switch (response.getCurstatus()) {
            case -4:
                jobControlVO.setCurstatus("取消预约");
                break;
            case -2:
                jobControlVO.setCurstatus("暂存");
                break;
            case -1:
                jobControlVO.setCurstatus("保存");
                break;
            case 0:
                jobControlVO.setCurstatus("计划中");
                break;
            case 1:
                jobControlVO.setCurstatus("申请");
                break;
            case 2:
                jobControlVO.setCurstatus("审批流转中");
                break;
            case 5:
                jobControlVO.setCurstatus("完成审批");
                break;
            case 6:
                jobControlVO.setCurstatus("审核不通过");
                break;
            case 7:
                jobControlVO.setCurstatus("已暂停");
                break;
            case 8:
                jobControlVO.setCurstatus("已取消");
                break;
            case 10:
                jobControlVO.setCurstatus("已关闭");
                break;
            case 11:
                jobControlVO.setCurstatus("后续日未确认");
                break;
            default:
                jobControlVO.setCurstatus("其他");
        }
        jobControlVO.setPx(response.getPx());
        jobControlVO.setPy(response.getPy());
        jobControlVO.setJl(response.getJl());
        jobControlVO.setUlist(jobControlPersonMapping.get(response.getOperationId()));
        return jobControlVO;
    }

    private static Map<BigDecimal, List<JobControlPersonVO>> getJobControlPersonMapping(List<HuiZhouWorkResponseDTO> responses) {
        Map<BigDecimal, List<JobControlPersonVO>> jobControlPersonMapping = new HashMap<>();
        for (HuiZhouWorkResponseDTO respons : responses) {
            if (CollectionUtils.isEmpty(jobControlPersonMapping.get(respons.getOperationId()))) {
                //获取人员
                List<JobControlPersonVO> jobControlPersonVOList = getPersonVOList(respons);
                jobControlPersonMapping.put(respons.getOperationId(), jobControlPersonVOList);
            } else {
                jobControlPersonMapping.get(respons.getOperationId()).add(getJobControlPersonVO(respons));
            }
        }
        return jobControlPersonMapping;
    }

    private static HttpEntity<MultiValueMap<String, Object>> getHttpEntity(JobControlDTO jobControlDTO) {
        MultiValueMap<String, Object> variables = new LinkedMultiValueMap<>(8);
        variables.add("longitude", jobControlDTO.getLongitude());
        variables.add("latitude", jobControlDTO.getLatitude());
        variables.add("workTime", jobControlDTO.getWorkTime());
        variables.add("radius", jobControlDTO.getRadius());
        List<HuiZhouWorkResponseDTO> responses;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(variables, headers);
        return httpEntity;
    }

    private static List<JobControlPersonVO> getPersonVOList(HuiZhouWorkResponseDTO respons) {
        List<JobControlPersonVO> jobControlPersonVOList = new ArrayList<>();
        JobControlPersonVO jobControlPersonVO = getJobControlPersonVO(respons);
        jobControlPersonVOList.add(jobControlPersonVO);
        return jobControlPersonVOList;
    }

    private static JobControlPersonVO getJobControlPersonVO(HuiZhouWorkResponseDTO respons) {
        JobControlPersonVO jobControlPersonVO = new JobControlPersonVO();
        jobControlPersonVO.setUname(respons.getUname());
        jobControlPersonVO.setUsex(respons.getUsex());
        jobControlPersonVO.setUmobile(respons.getUmobile());
        jobControlPersonVO.setUemail(respons.getUemail());

        return jobControlPersonVO;
    }

    private static String response = "[\n" +
            "    {\n" +
            "        \"operationId\": 714390,\n" +
            "        \"posId\": 647588,\n" +
            "        \"appDep\": \"7\",\n" +
            "        \"operLocation\": \"111芳烃装置吸附\",\n" +
            "        \"operContent\": \"111芳烃装置吸附单元防腐保温修复\",\n" +
            "        \"hse\": \"\",\n" +
            "        \"appuserId\": \"26268\",\n" +
            "        \"appTime\": \"2023-05-05 02:48:10\",\n" +
            "        \"plansTime\": \"2023-05-05 15:46:00\",\n" +
            "        \"planeTime\": \"2023-05-06 07:16:00\",\n" +
            "        \"stime\": \"2023-05-05 16:57:27\",\n" +
            "        \"etime\": null,\n" +
            "        \"isclear\": 0,\n" +
            "        \"curstatus\": 12,\n" +
            "        \"flownodeId\": 3,\n" +
            "        \"flownodeName\": \"2598|,2729703\",\n" +
            "        \"useeMail\": 0,\n" +
            "        \"usesms\": 0,\n" +
            "        \"hazard\": \",206,210,212\",\n" +
            "        \"opertypeId\": \"GENERAL\",\n" +
            "        \"ticketnum\": \"HSE-MF3-S11-2023--714390\",\n" +
            "        \"isurgent\": 0,\n" +
            "        \"hazardother\": \"\",\n" +
            "        \"opercatid\": \",1304\",\n" +
            "        \"monitoruserId\": \",26268\",\n" +
            "        \"authuserId\": \"\",\n" +
            "        \"pauseuser\": 0,\n" +
            "        \"createtime\": \"2023-01-31 01:46:16\",\n" +
            "        \"updatetime\": \"2023-05-05 02:48:10\",\n" +
            "        \"isdelay\": 0,\n" +
            "        \"delayremark\": \"\",\n" +
            "        \"runmonitor\": \"，24660\",\n" +
            "        \"runworkerBuilder\": \"\",\n" +
            "        \"runworker\": \"，260973，260969，260499，260976，261349\",\n" +
            "        \"runbuilderManager\": \"，269624\",\n" +
            "        \"runkeeper\": \"\",\n" +
            "        \"runkeeperBuilder\": \"，269624\",\n" +
            "        \"runclearChecker\": \"\",\n" +
            "        \"pauseprestate\": \"0\",\n" +
            "        \"consdep\": 11160,\n" +
            "        \"ticketlevel\": \"0\",\n" +
            "        \"opercatother\": \"\",\n" +
            "        \"jhaid\": 24755,\n" +
            "        \"runclearconschecker\": \"\",\n" +
            "        \"runclearchecktime\": null,\n" +
            "        \"runclearconscheckTime\": null,\n" +
            "        \"gasneedCount\": 0,\n" +
            "        \"changeTime\": \"2023-05-05 16:57:15\",\n" +
            "        \"posids\": \"234\",\n" +
            "        \"closeremark\": \"\",\n" +
            "        \"appuseriddelay\": null,\n" +
            "        \"appuseriddelayTime\": null,\n" +
            "        \"aproveuseriddelay\": null,\n" +
            "        \"aproveuseriddelayTime\": null,\n" +
            "        \"gpsx\": null,\n" +
            "        \"gpsy\": null,\n" +
            "        \"roadoccupation\": null,\n" +
            "        \"ulist\": [\n" +
            "            {\n" +
            "                \"userid\": 260499,\n" +
            "                \"depid\": 11160,\n" +
            "                \"uname\": \"陈海龙\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 26268,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"甘一凡\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"ganyf3@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 1301,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"黄佳富\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"huangjf2@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 24660,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"金鹏\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_01255@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 260969,\n" +
            "                \"depid\": 11160,\n" +
            "                \"uname\": \"李国行\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 260973,\n" +
            "                \"depid\": 11160,\n" +
            "                \"uname\": \"李万银\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 260976,\n" +
            "                \"depid\": 11160,\n" +
            "                \"uname\": \"聂增辉\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 25669,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"许俊杰\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_03944@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 261349,\n" +
            "                \"depid\": 11160,\n" +
            "                \"uname\": \"易发林\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 269624,\n" +
            "                \"depid\": 11160,\n" +
            "                \"uname\": \"赵艳玲\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"jl\": 0.0,\n" +
            "        \"px\": \"114.6041019566\",\n" +
            "        \"py\": \"22.7379943710\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationId\": 714395,\n" +
            "        \"posId\": 674705,\n" +
            "        \"appDep\": \"7\",\n" +
            "        \"operLocation\": \"111芳烃装置吸附\",\n" +
            "        \"operContent\": \"111芳烃装置吸附单元转动设备补油\",\n" +
            "        \"hse\": \"\",\n" +
            "        \"appuserId\": \"26268\",\n" +
            "        \"appTime\": \"2023-05-05 02:53:04\",\n" +
            "        \"plansTime\": \"2023-05-05 15:51:00\",\n" +
            "        \"planeTime\": \"2023-05-06 07:21:00\",\n" +
            "        \"stime\": \"2023-05-05 16:47:10\",\n" +
            "        \"etime\": null,\n" +
            "        \"isclear\": 0,\n" +
            "        \"curstatus\": 12,\n" +
            "        \"flownodeId\": 3,\n" +
            "        \"flownodeName\": \"2598|,2729739\",\n" +
            "        \"useeMail\": 0,\n" +
            "        \"usesms\": 0,\n" +
            "        \"hazard\": \",205,212,213\",\n" +
            "        \"opertypeId\": \"GENERAL\",\n" +
            "        \"ticketnum\": \"HSE-MF3-S11-2023--714395\",\n" +
            "        \"isurgent\": 0,\n" +
            "        \"hazardother\": \"\",\n" +
            "        \"opercatid\": \",1304\",\n" +
            "        \"monitoruserId\": \",26268\",\n" +
            "        \"authuserId\": \"\",\n" +
            "        \"pauseuser\": 0,\n" +
            "        \"createtime\": \"2023-03-28 18:32:22\",\n" +
            "        \"updatetime\": \"2023-05-05 02:53:04\",\n" +
            "        \"isdelay\": 0,\n" +
            "        \"delayremark\": \"\",\n" +
            "        \"runmonitor\": \"，24660\",\n" +
            "        \"runworkerBuilder\": \"\",\n" +
            "        \"runworker\": \"，29292，252415\",\n" +
            "        \"runbuilderManager\": \"，256470\",\n" +
            "        \"runkeeper\": \"\",\n" +
            "        \"runkeeperBuilder\": \"，256470\",\n" +
            "        \"runclearChecker\": \"\",\n" +
            "        \"pauseprestate\": \"0\",\n" +
            "        \"consdep\": 6078,\n" +
            "        \"ticketlevel\": \"0\",\n" +
            "        \"opercatother\": \"\",\n" +
            "        \"jhaid\": 7052,\n" +
            "        \"runclearconschecker\": \"\",\n" +
            "        \"runclearchecktime\": null,\n" +
            "        \"runclearconscheckTime\": null,\n" +
            "        \"gasneedCount\": 0,\n" +
            "        \"changeTime\": \"2023-05-05 16:46:57\",\n" +
            "        \"posids\": \"234\",\n" +
            "        \"closeremark\": \"\",\n" +
            "        \"appuseriddelay\": null,\n" +
            "        \"appuseriddelayTime\": null,\n" +
            "        \"aproveuseriddelay\": null,\n" +
            "        \"aproveuseriddelayTime\": null,\n" +
            "        \"gpsx\": null,\n" +
            "        \"gpsy\": null,\n" +
            "        \"roadoccupation\": null,\n" +
            "        \"ulist\": [\n" +
            "            {\n" +
            "                \"userid\": 252415,\n" +
            "                \"depid\": 6078,\n" +
            "                \"uname\": \"陈海\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 256470,\n" +
            "                \"depid\": 6078,\n" +
            "                \"uname\": \"陈钦寿\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 26268,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"甘一凡\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"ganyf3@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 29292,\n" +
            "                \"depid\": 6078,\n" +
            "                \"uname\": \"黄宏鑫\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_85662@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 1301,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"黄佳富\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"huangjf2@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 24660,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"金鹏\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_01255@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 25669,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"许俊杰\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_03944@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"jl\": 0.0,\n" +
            "        \"px\": \"114.6041019566\",\n" +
            "        \"py\": \"22.7379943710\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationId\": 714396,\n" +
            "        \"posId\": 676959,\n" +
            "        \"appDep\": \"7\",\n" +
            "        \"operLocation\": \"1# \",\n" +
            "        \"operContent\": \"111单元低温热项目清场吊装配合\",\n" +
            "        \"hse\": \"\",\n" +
            "        \"appuserId\": \"26268\",\n" +
            "        \"appTime\": \"2023-05-05 02:54:46\",\n" +
            "        \"plansTime\": \"2023-05-05 15:53:00\",\n" +
            "        \"planeTime\": \"2023-05-06 07:23:00\",\n" +
            "        \"stime\": \"2023-05-05 17:22:22\",\n" +
            "        \"etime\": null,\n" +
            "        \"isclear\": 0,\n" +
            "        \"curstatus\": 12,\n" +
            "        \"flownodeId\": 3,\n" +
            "        \"flownodeName\": \"1636|,2729744\",\n" +
            "        \"useeMail\": 0,\n" +
            "        \"usesms\": 0,\n" +
            "        \"hazard\": \",207,212\",\n" +
            "        \"opertypeId\": \"TICKET_HOIST_3\",\n" +
            "        \"ticketnum\": \"HSE-MF3-S15-2023--714396\",\n" +
            "        \"isurgent\": 0,\n" +
            "        \"hazardother\": \"\",\n" +
            "        \"opercatid\": \",1301\",\n" +
            "        \"monitoruserId\": \",26268\",\n" +
            "        \"authuserId\": \"\",\n" +
            "        \"pauseuser\": 0,\n" +
            "        \"createtime\": \"2023-03-30 04:26:55\",\n" +
            "        \"updatetime\": \"2023-05-05 02:54:46\",\n" +
            "        \"isdelay\": 0,\n" +
            "        \"delayremark\": \"\",\n" +
            "        \"runmonitor\": \"，24660\",\n" +
            "        \"runworkerBuilder\": \"\",\n" +
            "        \"runworker\": \"，266577\",\n" +
            "        \"runbuilderManager\": \"，266578\",\n" +
            "        \"runkeeper\": \"，24660\",\n" +
            "        \"runkeeperBuilder\": \"，24670\",\n" +
            "        \"runclearChecker\": \"\",\n" +
            "        \"pauseprestate\": \"0\",\n" +
            "        \"consdep\": 6116,\n" +
            "        \"ticketlevel\": \"2003\",\n" +
            "        \"opercatother\": \"\",\n" +
            "        \"jhaid\": 46516,\n" +
            "        \"runclearconschecker\": \"\",\n" +
            "        \"runclearchecktime\": null,\n" +
            "        \"runclearconscheckTime\": null,\n" +
            "        \"gasneedCount\": 0,\n" +
            "        \"changeTime\": \"2023-05-05 17:22:10\",\n" +
            "        \"posids\": \"234\",\n" +
            "        \"closeremark\": \"\",\n" +
            "        \"appuseriddelay\": null,\n" +
            "        \"appuseriddelayTime\": null,\n" +
            "        \"aproveuseriddelay\": null,\n" +
            "        \"aproveuseriddelayTime\": null,\n" +
            "        \"gpsx\": 114.585132,\n" +
            "        \"gpsy\": 22.751375,\n" +
            "        \"roadoccupation\": null,\n" +
            "        \"ulist\": [\n" +
            "            {\n" +
            "                \"userid\": 26268,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"甘一凡\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"ganyf3@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 1301,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"黄佳富\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"huangjf2@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 24660,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"金鹏\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_01255@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 266577,\n" +
            "                \"depid\": 10915,\n" +
            "                \"uname\": \"杨志峰\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 24670,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"于红江\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_01268@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 266578,\n" +
            "                \"depid\": 10915,\n" +
            "                \"uname\": \"赵健\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"jl\": 0.0,\n" +
            "        \"px\": \"114.6041019566\",\n" +
            "        \"py\": \"22.7379943710\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationId\": 714398,\n" +
            "        \"posId\": 676964,\n" +
            "        \"appDep\": \"7\",\n" +
            "        \"operLocation\": \"1#\",\n" +
            "        \"operContent\": \"111芳烃装置单元低温热项目脚手架施工\",\n" +
            "        \"hse\": \"\",\n" +
            "        \"appuserId\": \"26268\",\n" +
            "        \"appTime\": \"2023-05-05 02:57:12\",\n" +
            "        \"plansTime\": \"2023-05-05 15:55:00\",\n" +
            "        \"planeTime\": \"2023-05-06 07:25:00\",\n" +
            "        \"stime\": \"2023-05-05 17:06:38\",\n" +
            "        \"etime\": null,\n" +
            "        \"isclear\": 0,\n" +
            "        \"curstatus\": 12,\n" +
            "        \"flownodeId\": 3,\n" +
            "        \"flownodeName\": \"1821|,2729763\",\n" +
            "        \"useeMail\": 0,\n" +
            "        \"usesms\": 0,\n" +
            "        \"hazard\": \",206,212\",\n" +
            "        \"opertypeId\": \"SUSPEND_TICKET_3\",\n" +
            "        \"ticketnum\": \"HSE-MF3-S06-2023--714398\",\n" +
            "        \"isurgent\": 0,\n" +
            "        \"hazardother\": \"\",\n" +
            "        \"opercatid\": \",1301\",\n" +
            "        \"monitoruserId\": \",26268\",\n" +
            "        \"authuserId\": \"\",\n" +
            "        \"pauseuser\": 0,\n" +
            "        \"createtime\": \"2023-03-30 04:32:53\",\n" +
            "        \"updatetime\": \"2023-05-05 02:57:12\",\n" +
            "        \"isdelay\": 0,\n" +
            "        \"delayremark\": \"\",\n" +
            "        \"runmonitor\": \"，24660\",\n" +
            "        \"runworkerBuilder\": \"\",\n" +
            "        \"runworker\": \"，267403\",\n" +
            "        \"runbuilderManager\": \"，268814\",\n" +
            "        \"runkeeper\": \"，24670\",\n" +
            "        \"runkeeperBuilder\": \"，267404\",\n" +
            "        \"runclearChecker\": \"\",\n" +
            "        \"pauseprestate\": \"0\",\n" +
            "        \"consdep\": 6116,\n" +
            "        \"ticketlevel\": \"4004\",\n" +
            "        \"opercatother\": \"\",\n" +
            "        \"jhaid\": 44780,\n" +
            "        \"runclearconschecker\": \"\",\n" +
            "        \"runclearchecktime\": null,\n" +
            "        \"runclearconscheckTime\": null,\n" +
            "        \"gasneedCount\": 0,\n" +
            "        \"changeTime\": \"2023-05-05 17:06:26\",\n" +
            "        \"posids\": \"234\",\n" +
            "        \"closeremark\": \"\",\n" +
            "        \"appuseriddelay\": null,\n" +
            "        \"appuseriddelayTime\": null,\n" +
            "        \"aproveuseriddelay\": null,\n" +
            "        \"aproveuseriddelayTime\": null,\n" +
            "        \"gpsx\": 114.585199,\n" +
            "        \"gpsy\": 22.751364,\n" +
            "        \"roadoccupation\": null,\n" +
            "        \"ulist\": [\n" +
            "            {\n" +
            "                \"userid\": 267404,\n" +
            "                \"depid\": 10915,\n" +
            "                \"uname\": \"常建朋\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 267403,\n" +
            "                \"depid\": 10915,\n" +
            "                \"uname\": \"邓迎会\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 26268,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"甘一凡\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"ganyf3@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 1301,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"黄佳富\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"huangjf2@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 24660,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"金鹏\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_01255@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 268814,\n" +
            "                \"depid\": 10915,\n" +
            "                \"uname\": \"魏培胜\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 24670,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"于红江\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_01268@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"jl\": 0.0,\n" +
            "        \"px\": \"114.6041019566\",\n" +
            "        \"py\": \"22.7379943710\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationId\": 714435,\n" +
            "        \"posId\": 712586,\n" +
            "        \"appDep\": \"7\",\n" +
            "        \"operLocation\": \"R401\",\n" +
            "        \"operContent\": \"R401盲板拆除\",\n" +
            "        \"hse\": \"\",\n" +
            "        \"appuserId\": \"108614\",\n" +
            "        \"appTime\": \"2023-05-05 03:45:59\",\n" +
            "        \"plansTime\": \"2023-05-05 16:00:00\",\n" +
            "        \"planeTime\": \"2023-05-06 04:00:00\",\n" +
            "        \"stime\": \"2023-05-05 16:41:19\",\n" +
            "        \"etime\": null,\n" +
            "        \"isclear\": 0,\n" +
            "        \"curstatus\": 12,\n" +
            "        \"flownodeId\": 3,\n" +
            "        \"flownodeName\": \"2635|,2729987\",\n" +
            "        \"useeMail\": 0,\n" +
            "        \"usesms\": 0,\n" +
            "        \"hazard\": \",212\",\n" +
            "        \"opertypeId\": \"BLIND\",\n" +
            "        \"ticketnum\": \"HSE-MF3-S07-2023--714435\",\n" +
            "        \"isurgent\": 0,\n" +
            "        \"hazardother\": \"\",\n" +
            "        \"opercatid\": \",1304\",\n" +
            "        \"monitoruserId\": \",108614\",\n" +
            "        \"authuserId\": \"\",\n" +
            "        \"pauseuser\": 0,\n" +
            "        \"createtime\": \"2023-05-01 07:06:10\",\n" +
            "        \"updatetime\": \"2023-05-05 03:45:59\",\n" +
            "        \"isdelay\": 0,\n" +
            "        \"delayremark\": \"\",\n" +
            "        \"runmonitor\": \"，24660\",\n" +
            "        \"runworkerBuilder\": \"\",\n" +
            "        \"runworker\": \"，268868，225015，268866，268867\",\n" +
            "        \"runbuilderManager\": \"，268865\",\n" +
            "        \"runkeeper\": \"，25669\",\n" +
            "        \"runkeeperBuilder\": \"，268865\",\n" +
            "        \"runclearChecker\": \"\",\n" +
            "        \"pauseprestate\": \"0\",\n" +
            "        \"consdep\": 6078,\n" +
            "        \"ticketlevel\": \"0\",\n" +
            "        \"opercatother\": \"\",\n" +
            "        \"jhaid\": 46276,\n" +
            "        \"runclearconschecker\": \"\",\n" +
            "        \"runclearchecktime\": null,\n" +
            "        \"runclearconscheckTime\": null,\n" +
            "        \"gasneedCount\": 0,\n" +
            "        \"changeTime\": \"2023-05-05 16:41:08\",\n" +
            "        \"posids\": \"234\",\n" +
            "        \"closeremark\": \"\",\n" +
            "        \"appuseriddelay\": null,\n" +
            "        \"appuseriddelayTime\": null,\n" +
            "        \"aproveuseriddelay\": null,\n" +
            "        \"aproveuseriddelayTime\": null,\n" +
            "        \"gpsx\": 114.585303,\n" +
            "        \"gpsy\": 22.751358,\n" +
            "        \"roadoccupation\": null,\n" +
            "        \"ulist\": [\n" +
            "            {\n" +
            "                \"userid\": 268867,\n" +
            "                \"depid\": 6078,\n" +
            "                \"uname\": \"陈木生\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 26255,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"韩文华\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"hanwh6@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 24660,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"金鹏\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_01255@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 225015,\n" +
            "                \"depid\": 6078,\n" +
            "                \"uname\": \"梁智信\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 268868,\n" +
            "                \"depid\": 6078,\n" +
            "                \"uname\": \"谭国钦\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 268865,\n" +
            "                \"depid\": 6078,\n" +
            "                \"uname\": \"谭火明\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 108614,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"王一帆\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"wangyf82@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 25669,\n" +
            "                \"depid\": 7,\n" +
            "                \"uname\": \"许俊杰\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_03944@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 268866,\n" +
            "                \"depid\": 6078,\n" +
            "                \"uname\": \"张瑞祥\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"jl\": 0.0,\n" +
            "        \"px\": \"114.6041019566\",\n" +
            "        \"py\": \"22.7379943710\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationId\": 714657,\n" +
            "        \"posId\": 638756,\n" +
            "        \"appDep\": \"16\",\n" +
            "        \"operLocation\": \"122-K-601B\",\n" +
            "        \"operContent\": \"122单元压缩机K-601B盲板拆装作业\",\n" +
            "        \"hse\": \"\",\n" +
            "        \"appuserId\": \"32699\",\n" +
            "        \"appTime\": \"2023-05-05 17:27:04\",\n" +
            "        \"plansTime\": \"2023-05-05 17:26:00\",\n" +
            "        \"planeTime\": \"2023-05-06 04:00:00\",\n" +
            "        \"stime\": \"2023-05-05 17:37:15\",\n" +
            "        \"etime\": null,\n" +
            "        \"isclear\": 0,\n" +
            "        \"curstatus\": 12,\n" +
            "        \"flownodeId\": 3,\n" +
            "        \"flownodeName\": \"2637|,2731180\",\n" +
            "        \"useeMail\": 0,\n" +
            "        \"usesms\": 0,\n" +
            "        \"hazard\": \",201,203,212\",\n" +
            "        \"opertypeId\": \"BLIND\",\n" +
            "        \"ticketnum\": \"HSE-MF5-S07-2023--714657\",\n" +
            "        \"isurgent\": 0,\n" +
            "        \"hazardother\": \"\",\n" +
            "        \"opercatid\": \",1303\",\n" +
            "        \"monitoruserId\": \",@32699_25200\",\n" +
            "        \"authuserId\": \"\",\n" +
            "        \"pauseuser\": 0,\n" +
            "        \"createtime\": \"2022-12-15 15:45:37\",\n" +
            "        \"updatetime\": \"2023-05-05 17:27:04\",\n" +
            "        \"isdelay\": 0,\n" +
            "        \"delayremark\": \"\",\n" +
            "        \"runmonitor\": \"，25082\",\n" +
            "        \"runworkerBuilder\": \"\",\n" +
            "        \"runworker\": \"，262348\",\n" +
            "        \"runbuilderManager\": \"，27231\",\n" +
            "        \"runkeeper\": \"，25507\",\n" +
            "        \"runkeeperBuilder\": \"，259655\",\n" +
            "        \"runclearChecker\": \"\",\n" +
            "        \"pauseprestate\": \"0\",\n" +
            "        \"consdep\": 6079,\n" +
            "        \"ticketlevel\": \"0\",\n" +
            "        \"opercatother\": \"\",\n" +
            "        \"jhaid\": 47467,\n" +
            "        \"runclearconschecker\": \"\",\n" +
            "        \"runclearchecktime\": null,\n" +
            "        \"runclearconscheckTime\": null,\n" +
            "        \"gasneedCount\": 0,\n" +
            "        \"changeTime\": \"2023-05-05 17:37:03\",\n" +
            "        \"posids\": \"1534\",\n" +
            "        \"closeremark\": \"\",\n" +
            "        \"appuseriddelay\": null,\n" +
            "        \"appuseriddelayTime\": null,\n" +
            "        \"aproveuseriddelay\": null,\n" +
            "        \"aproveuseriddelayTime\": null,\n" +
            "        \"gpsx\": 0.000000,\n" +
            "        \"gpsy\": 0.000000,\n" +
            "        \"roadoccupation\": null,\n" +
            "        \"ulist\": [\n" +
            "            {\n" +
            "                \"userid\": 259655,\n" +
            "                \"depid\": 6079,\n" +
            "                \"uname\": \"关磊磊\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 25082,\n" +
            "                \"depid\": 16,\n" +
            "                \"uname\": \"郭兴宇\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_01848@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 26299,\n" +
            "                \"depid\": 16,\n" +
            "                \"uname\": \"何阳\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_04620@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 25200,\n" +
            "                \"depid\": 16,\n" +
            "                \"uname\": \"胡林杰\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"hulj2@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 27231,\n" +
            "                \"depid\": 6079,\n" +
            "                \"uname\": \"雷永强\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_55294@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 25507,\n" +
            "                \"depid\": 16,\n" +
            "                \"uname\": \"刘想\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_03773@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 4273,\n" +
            "                \"depid\": 16,\n" +
            "                \"uname\": \"王增龙\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"wangzl15@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 262348,\n" +
            "                \"depid\": 6079,\n" +
            "                \"uname\": \"翟世想\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"jl\": 0.0,\n" +
            "        \"px\": \"114.6041019566\",\n" +
            "        \"py\": \"22.7379943710\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationId\": 714111,\n" +
            "        \"posId\": 646920,\n" +
            "        \"appDep\": \"18\",\n" +
            "        \"operLocation\": \"307-P-107B油泥提升泵\",\n" +
            "        \"operContent\": \"307-P-107B油泥提升泵检修\",\n" +
            "        \"hse\": \"\",\n" +
            "        \"appuserId\": \"33442\",\n" +
            "        \"appTime\": \"2023-05-04 20:48:29\",\n" +
            "        \"plansTime\": \"2023-05-05 16:30:00\",\n" +
            "        \"planeTime\": \"2023-05-06 02:00:00\",\n" +
            "        \"stime\": \"2023-05-05 16:47:25\",\n" +
            "        \"etime\": null,\n" +
            "        \"isclear\": 0,\n" +
            "        \"curstatus\": 12,\n" +
            "        \"flownodeId\": 3,\n" +
            "        \"flownodeName\": \"2972|,2728292\",\n" +
            "        \"useeMail\": 0,\n" +
            "        \"usesms\": 0,\n" +
            "        \"hazard\": \",201,202,205,207,212,213,其他,401,402,405,其他\",\n" +
            "        \"opertypeId\": \"TICKET_MOVING_DEVICE\",\n" +
            "        \"ticketnum\": \"EM-PO-S17-2023--714111\",\n" +
            "        \"isurgent\": 0,\n" +
            "        \"hazardother\": \"机械伤害\",\n" +
            "        \"opercatid\": \",1303\",\n" +
            "        \"monitoruserId\": \",33442\",\n" +
            "        \"authuserId\": \"\",\n" +
            "        \"pauseuser\": 0,\n" +
            "        \"createtime\": \"2023-01-19 23:38:49\",\n" +
            "        \"updatetime\": \"2023-05-04 20:48:29\",\n" +
            "        \"isdelay\": 0,\n" +
            "        \"delayremark\": \"\",\n" +
            "        \"runmonitor\": \"，24254\",\n" +
            "        \"runworkerBuilder\": \"\",\n" +
            "        \"runworker\": \"，229839\",\n" +
            "        \"runbuilderManager\": \"，27362\",\n" +
            "        \"runkeeper\": \"，33375\",\n" +
            "        \"runkeeperBuilder\": \"，259358\",\n" +
            "        \"runclearChecker\": \"\",\n" +
            "        \"pauseprestate\": \"0\",\n" +
            "        \"consdep\": 5551,\n" +
            "        \"ticketlevel\": \"0\",\n" +
            "        \"opercatother\": \"\",\n" +
            "        \"jhaid\": 44544,\n" +
            "        \"runclearconschecker\": \"\",\n" +
            "        \"runclearchecktime\": null,\n" +
            "        \"runclearconscheckTime\": null,\n" +
            "        \"gasneedCount\": 0,\n" +
            "        \"changeTime\": \"2023-05-05 16:47:13\",\n" +
            "        \"posids\": \"332\",\n" +
            "        \"closeremark\": \"\",\n" +
            "        \"appuseriddelay\": null,\n" +
            "        \"appuseriddelayTime\": null,\n" +
            "        \"aproveuseriddelay\": null,\n" +
            "        \"aproveuseriddelayTime\": null,\n" +
            "        \"gpsx\": 114.599098,\n" +
            "        \"gpsy\": 22.742497,\n" +
            "        \"roadoccupation\": null,\n" +
            "        \"ulist\": [\n" +
            "            {\n" +
            "                \"userid\": 24254,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"安冠文\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_00653@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 259358,\n" +
            "                \"depid\": 5551,\n" +
            "                \"uname\": \"陈创辉\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 26278,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"董雄文\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"dongxw2@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 229839,\n" +
            "                \"depid\": 5551,\n" +
            "                \"uname\": \"何弦\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 33375,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"卢紫伟\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_81056@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 27362,\n" +
            "                \"depid\": 5551,\n" +
            "                \"uname\": \"孙斌\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_56766@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 33442,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"王祥涛\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"wangxt15@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"jl\": 138.0,\n" +
            "        \"px\": \"114.6036110815\",\n" +
            "        \"py\": \"22.7391566866\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationId\": 714683,\n" +
            "        \"posId\": 646920,\n" +
            "        \"appDep\": \"18\",\n" +
            "        \"operLocation\": \"307-P-401AB回用水泵\",\n" +
            "        \"operContent\": \"307-P-401AB回用水泵检修\",\n" +
            "        \"hse\": \"\",\n" +
            "        \"appuserId\": \"33442\",\n" +
            "        \"appTime\": \"2023-05-05 18:19:11\",\n" +
            "        \"plansTime\": \"2023-05-05 18:30:00\",\n" +
            "        \"planeTime\": \"2023-05-06 02:00:00\",\n" +
            "        \"stime\": \"2023-05-05 18:59:14\",\n" +
            "        \"etime\": null,\n" +
            "        \"isclear\": 0,\n" +
            "        \"curstatus\": 12,\n" +
            "        \"flownodeId\": 3,\n" +
            "        \"flownodeName\": \"1640|,2731313\",\n" +
            "        \"useeMail\": 0,\n" +
            "        \"usesms\": 0,\n" +
            "        \"hazard\": \",205,207,212,213,405\",\n" +
            "        \"opertypeId\": \"TICKET_HOIST_3\",\n" +
            "        \"ticketnum\": \"EM-PO-S15-2023--714683\",\n" +
            "        \"isurgent\": 0,\n" +
            "        \"hazardother\": \"\",\n" +
            "        \"opercatid\": \",1303\",\n" +
            "        \"monitoruserId\": \",33442\",\n" +
            "        \"authuserId\": \"\",\n" +
            "        \"pauseuser\": 0,\n" +
            "        \"createtime\": \"2023-01-19 23:38:49\",\n" +
            "        \"updatetime\": \"2023-05-05 18:19:11\",\n" +
            "        \"isdelay\": 0,\n" +
            "        \"delayremark\": \"\",\n" +
            "        \"runmonitor\": \"，24254\",\n" +
            "        \"runworkerBuilder\": \"\",\n" +
            "        \"runworker\": \"，27191\",\n" +
            "        \"runbuilderManager\": \"，27362\",\n" +
            "        \"runkeeper\": \"，33375\",\n" +
            "        \"runkeeperBuilder\": \"，260909\",\n" +
            "        \"runclearChecker\": \"\",\n" +
            "        \"pauseprestate\": \"0\",\n" +
            "        \"consdep\": 5551,\n" +
            "        \"ticketlevel\": \"2003\",\n" +
            "        \"opercatother\": \"\",\n" +
            "        \"jhaid\": 44544,\n" +
            "        \"runclearconschecker\": \"\",\n" +
            "        \"runclearchecktime\": null,\n" +
            "        \"runclearconscheckTime\": null,\n" +
            "        \"gasneedCount\": 0,\n" +
            "        \"changeTime\": \"2023-05-05 18:59:01\",\n" +
            "        \"posids\": \"332\",\n" +
            "        \"closeremark\": \"\",\n" +
            "        \"appuseriddelay\": null,\n" +
            "        \"appuseriddelayTime\": null,\n" +
            "        \"aproveuseriddelay\": null,\n" +
            "        \"aproveuseriddelayTime\": null,\n" +
            "        \"gpsx\": 114.598822,\n" +
            "        \"gpsy\": 22.742125,\n" +
            "        \"roadoccupation\": null,\n" +
            "        \"ulist\": [\n" +
            "            {\n" +
            "                \"userid\": 24254,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"安冠文\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_00653@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 16924,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"陈伟\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"chenwei71@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 260909,\n" +
            "                \"depid\": 5551,\n" +
            "                \"uname\": \"李云峰\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 27191,\n" +
            "                \"depid\": 5551,\n" +
            "                \"uname\": \"刘红平\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_54936@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 33375,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"卢紫伟\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_81056@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 27362,\n" +
            "                \"depid\": 5551,\n" +
            "                \"uname\": \"孙斌\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_56766@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 33442,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"王祥涛\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"wangxt15@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"jl\": 138.0,\n" +
            "        \"px\": \"114.6036110815\",\n" +
            "        \"py\": \"22.7391566866\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationId\": 714310,\n" +
            "        \"posId\": 646920,\n" +
            "        \"appDep\": \"18\",\n" +
            "        \"operLocation\": \"307-P-401AB回用水泵\",\n" +
            "        \"operContent\": \"307-P-401AB回用水泵检修\",\n" +
            "        \"hse\": \"\",\n" +
            "        \"appuserId\": \"33442\",\n" +
            "        \"appTime\": \"2023-05-05 01:31:51\",\n" +
            "        \"plansTime\": \"2023-05-05 16:30:00\",\n" +
            "        \"planeTime\": \"2023-05-06 02:00:00\",\n" +
            "        \"stime\": \"2023-05-05 16:48:54\",\n" +
            "        \"etime\": null,\n" +
            "        \"isclear\": 0,\n" +
            "        \"curstatus\": 12,\n" +
            "        \"flownodeId\": 3,\n" +
            "        \"flownodeName\": \"2972|,2729256\",\n" +
            "        \"useeMail\": 0,\n" +
            "        \"usesms\": 0,\n" +
            "        \"hazard\": \",205,207,212,213,其他,405,其他\",\n" +
            "        \"opertypeId\": \"TICKET_MOVING_DEVICE\",\n" +
            "        \"ticketnum\": \"EM-PO-S17-2023--714310\",\n" +
            "        \"isurgent\": 0,\n" +
            "        \"hazardother\": \"机械伤害\",\n" +
            "        \"opercatid\": \",1303\",\n" +
            "        \"monitoruserId\": \",33442\",\n" +
            "        \"authuserId\": \"\",\n" +
            "        \"pauseuser\": 0,\n" +
            "        \"createtime\": \"2023-01-19 23:38:49\",\n" +
            "        \"updatetime\": \"2023-05-05 01:31:51\",\n" +
            "        \"isdelay\": 0,\n" +
            "        \"delayremark\": \"\",\n" +
            "        \"runmonitor\": \"，24254\",\n" +
            "        \"runworkerBuilder\": \"\",\n" +
            "        \"runworker\": \"，260909\",\n" +
            "        \"runbuilderManager\": \"，27362\",\n" +
            "        \"runkeeper\": \"，33375\",\n" +
            "        \"runkeeperBuilder\": \"，259356\",\n" +
            "        \"runclearChecker\": \"\",\n" +
            "        \"pauseprestate\": \"0\",\n" +
            "        \"consdep\": 5551,\n" +
            "        \"ticketlevel\": \"0\",\n" +
            "        \"opercatother\": \"\",\n" +
            "        \"jhaid\": 48535,\n" +
            "        \"runclearconschecker\": \"\",\n" +
            "        \"runclearchecktime\": null,\n" +
            "        \"runclearconscheckTime\": null,\n" +
            "        \"gasneedCount\": 0,\n" +
            "        \"changeTime\": \"2023-05-05 16:48:42\",\n" +
            "        \"posids\": \"332\",\n" +
            "        \"closeremark\": \"\",\n" +
            "        \"appuseriddelay\": null,\n" +
            "        \"appuseriddelayTime\": null,\n" +
            "        \"aproveuseriddelay\": null,\n" +
            "        \"aproveuseriddelayTime\": null,\n" +
            "        \"gpsx\": 114.598930,\n" +
            "        \"gpsy\": 22.742315,\n" +
            "        \"roadoccupation\": null,\n" +
            "        \"ulist\": [\n" +
            "            {\n" +
            "                \"userid\": 24254,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"安冠文\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_00653@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 26278,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"董雄文\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"dongxw2@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 260909,\n" +
            "                \"depid\": 5551,\n" +
            "                \"uname\": \"李云峰\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 33375,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"卢紫伟\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_81056@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 27362,\n" +
            "                \"depid\": 5551,\n" +
            "                \"uname\": \"孙斌\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"CON_56766@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 33442,\n" +
            "                \"depid\": 18,\n" +
            "                \"uname\": \"王祥涛\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"wangxt15@cnooc.com.cn\",\n" +
            "                \"umobile\": \"\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"userid\": 259356,\n" +
            "                \"depid\": 5551,\n" +
            "                \"uname\": \"杨锦坤\",\n" +
            "                \"usex\": \"男\",\n" +
            "                \"uemail\": \"\",\n" +
            "                \"umobile\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"jl\": 138.0,\n" +
            "        \"px\": \"114.6036110815\",\n" +
            "        \"py\": \"22.7391566866\"\n" +
            "    }\n" +
            "]";
}
