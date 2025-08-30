package cc.sofast.framework.starter.mybatis.mapper.req;

import cc.sofast.framework.starter.beansearch.Condition;
import cc.sofast.framework.starter.beansearch.Where;
import cc.sofast.framework.starter.mybatis.mapper.AbConditionQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQueryReq extends AbConditionQuery {

    @Where(value = Condition.like)
    private String name;

}
