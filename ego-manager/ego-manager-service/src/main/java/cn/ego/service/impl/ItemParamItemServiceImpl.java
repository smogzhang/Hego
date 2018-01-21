package cn.ego.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ego.mapper.TbItemParamItemMapper;
import cn.ego.pojo.TbItemParamItem;
import cn.ego.pojo.TbItemParamItemExample;
import cn.ego.pojo.TbItemParamItemExample.Criteria;
import cn.ego.service.ItemParamItemService;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper; 
	
	@Override
	public String getParamItemById(long itemid) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemid);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0) {
			return "";
		}
		TbItemParamItem itemParamItem = list.get(0);
		return itemParamItem.getParamData();
		/*
		 	修改为表格格式后输出，先转换为Java对象，再拼接字符串。
		 	//把json数据转换成java对象
			List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
			//将参数信息转换成html
			StringBuffer sb = new StringBuffer(); 
			//sb.append("<div>");
			sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
			sb.append("    <tbody>\n");
			for (Map map : paramList) {
				sb.append("        <tr>\n");
				sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
				sb.append("        </tr>\n");
				List<Map> params = (List<Map>) map.get("params");
				for (Map map2 : params) {
					sb.append("        <tr>\n");
					sb.append("            <td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
					sb.append("            <td>"+map2.get("v")+"</td>\n");
					sb.append("        </tr>\n");
				}
			}
			sb.append("    </tbody>\n");
			sb.append("</table>");
			//sb.append("</div>");
			return sb.toString();
		 */
	}

}
