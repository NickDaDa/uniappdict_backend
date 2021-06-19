/**
 * 
 */
package com.jeecg.devicemanage.wx.enhance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecg.devicemanage.wx.enhance.anno.UniAppDict;
import com.jeecg.devicemanage.wx.enhance.service.EnhancerService;
import com.jeecg.devicemanage.wx.enhance.vo.DictVo;


/**
 * 
 * @author renqing
 *
 * Jun 16, 2021
 */
@Service("enhancerService")
public class EnhancerServiceImpl implements EnhancerService {
	
	@Autowired
	private SystemService systemService;

	/* (non-Javadoc)
	 * @see com.jeecg.devicemanage.wx.enhance.service.EnhancerService#simpleSelect(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String simpleSelect(String dictGroupCode, String codeVal) {
		StringBuffer typeTxtQuery  = new StringBuffer("SELECT typename FROM t_s_type tst WHERE tst.typegroupid = (" );
		typeTxtQuery.append("SELECT id FROM t_s_typegroup tstg WHERE tstg.typegroupcode = :dictGroupCode)");
		typeTxtQuery.append(" AND typecode = :typeCode ");
		SQLQuery sbTypeQuery = systemService.getSession().createSQLQuery(typeTxtQuery.toString());
		sbTypeQuery.setString("dictGroupCode", dictGroupCode);
		sbTypeQuery.setString("typeCode", codeVal);
		List<String> text = sbTypeQuery.list();
		if (null != text && text.size() > 0) {
			return text.get(0);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jeecg.devicemanage.wx.enhance.service.EnhancerService#selectionSel(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DictVo> selectionSel(String dictGroupCode) {
		String sql = " SELECT * FROM T_S_TYPE tst WHERE tst.TYPEGROUPID = (SELECT ID FROM T_S_TYPEGROUP tstg WHERE tstg.TYPEGROUPCODE = :dictGroupCode)";
		SQLQuery sqlQuery = systemService.getSession().createSQLQuery(sql.toString()).addEntity(TSType.class);
		sqlQuery.setString("dictGroupCode", dictGroupCode);
		List<TSType> list = sqlQuery.list();
		List<DictVo> dictList = new ArrayList<DictVo>();
		for (TSType type : list) {
			DictVo temp = new DictVo();
			temp.setCode(type.getTypecode());
			temp.setText(type.getTypename());
			dictList.add(temp);
		}
		list = null;
		return dictList;
//		JSONArray array= JSONArray.parseArray(JSON.toJSONString(dictList));
//		return array.toJSONString();
//		JSONArray array= JSONArray.parseArray(JSON.toString(faultTypeList))
		
	}

	@Override
	public String specialSel(UniAppDict anno) {
		// TODO Auto-generated method stub
		return null;
	}

}
