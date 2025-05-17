package cc.sofast.biz.component.file;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wxl
 */
@Service
@RequiredArgsConstructor
public class FileService extends ServiceImpl<FileMapper, FileDO> {


    /**
     * 分页查询
     * TODO 只能看到公共的和自己上传的
     *
     * @param objectPage 分页参数
     * @param params     查询参数
     * @return 分页结果
     */
    public Page<FileDO> selectPage(Page<Object> objectPage, FileUploadParams params) {


        return null;
    }
}
