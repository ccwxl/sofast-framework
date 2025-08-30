package cc.sofast.framework.starter.mybatis.mapper;

import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.mybatis.mapper.req.StudentQueryReq;
import cc.sofast.framework.starter.mybatis.mapper.res.StudentRes;
import org.mockito.Mockito;

class SofastBaseMapperTest {

    @org.junit.jupiter.api.Test
    void selectPage() {
        StudentMapper mapper = Mockito.mock(StudentMapper.class);
        StudentQueryReq queryReq = new StudentQueryReq();
        PageResult<StudentRes> classPageResult = mapper.selectPage(queryReq, StudentRes.class);

    }
}