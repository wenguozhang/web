package com.wgz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wgz.dao.NccSlShysMapper;
import com.wgz.pojo.NccSlShys;
import com.wgz.pojo.NccTaskRunInfo;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class NccSLShysService {
	@Autowired
	private NccSlShysMapper nccSlShysMapper;
	public void insertOrUpdate(NccTaskRunInfo nccRun, List<NccSlShys> lists){
		if(lists.size() == 0)
			return;
		for(NccSlShys nccSlShys : lists) {
			log.info(nccSlShys.toString());
			NccSlShys data = nccSlShysMapper.selectOne(new QueryWrapper<NccSlShys>().eq("slid", nccSlShys.getSlid()));
			if(data == null) {//新增
				log.info("======================insert===============================");
				nccSlShysMapper.insert(nccSlShys);
			} else {//修改
				//判断三方库数据状态是否为1
				if("1".equals(data.getStatus())) 
					continue;
				nccSlShysMapper.update(nccSlShys, new UpdateWrapper<NccSlShys>().eq("slid", nccSlShys.getSlid()));
			}
		}
	}

}
