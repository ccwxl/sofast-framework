package cc.sofast.biz.component.file;

import cc.sofast.framework.starter.common.utils.json.JsonUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.hash.HashInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 用来将文件上传记录保存到数据库，这里使用了 MyBatis-Plus 和 Hutool 工具类
 *
 * @author wxl
 */
@Component
@RequiredArgsConstructor
public class SofastFileRecorder implements FileRecorder {

    private final FileMapper fileMapper;

    /**
     * 保存文件信息到数据库
     */
    @SneakyThrows
    @Override
    public boolean save(FileInfo info) {
        FileDO detail = toFileDetail(info);
        detail.setId(info.getFilename());
        boolean b = fileMapper.insert(detail) >= 1;
        if (b) {
            info.setId(detail.getId());
        }
        return b;
    }

    /**
     * 更新文件记录，可以根据文件 ID 或 URL 来更新文件记录，
     * 主要用在手动分片上传文件-完成上传，作用是更新文件信息
     */
    @SneakyThrows
    @Override
    public void update(FileInfo info) {
        FileDO detail = toFileDetail(info);
        QueryWrapper<FileDO> qw = new QueryWrapper<FileDO>()
                .eq(detail.getUrl() != null, FileDO.Fields.url, detail.getUrl())
                .eq(detail.getId() != null, FileDO.Fields.id, detail.getId());
        fileMapper.update(detail, qw);
    }

    /**
     * 根据 url 查询文件信息
     */
    @SneakyThrows
    @Override
    public FileInfo getByUrl(String url) {
        return toFileInfo(fileMapper.selectOne(new QueryWrapper<FileDO>().eq(FileDO.Fields.url, url)));
    }

    /**
     * 根据 url 删除文件信息
     */
    @Override
    public boolean delete(String url) {
        fileMapper.delete(new QueryWrapper<FileDO>().eq(FileDO.Fields.url, url));
        return true;
    }

    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {
        throw new UnsupportedOperationException("暂不支持!");
    }

    @Override
    public void deleteFilePartByUploadId(String uploadId) {
        throw new UnsupportedOperationException("暂不支持!");
    }

    /**
     * 将 FileInfo 转为 FileDetail
     */
    public FileDO toFileDetail(FileInfo info) {
        FileDO detail = BeanUtil.copyProperties(
                info, FileDO.class, "metadata", "userMetadata", "thMetadata", "thUserMetadata", "attr", "hashInfo");
        // 这里手动获 元数据 并转成 json 字符串，方便存储在数据库中
        detail.setMetadata(valueToJson(info.getMetadata()));
        detail.setUserMetadata(valueToJson(info.getUserMetadata()));
        detail.setThMetadata(valueToJson(info.getThMetadata()));
        detail.setThUserMetadata(valueToJson(info.getThUserMetadata()));
        // 这里手动获 取附加属性字典 并转成 json 字符串，方便存储在数据库中
        detail.setAttr(valueToJson(info.getAttr()));
        // 这里手动获 哈希信息 并转成 json 字符串，方便存储在数据库中
        detail.setHashInfo(valueToJson(info.getHashInfo()));
        //TODO tenantId process
//        detail.setTenantId(LoginHelper.getTenantId());
        return detail;
    }

    /**
     * 将 FileDetail 转为 FileInfo
     */
    public FileInfo toFileInfo(FileDO detail) {
        FileInfo info = BeanUtil.copyProperties(
                detail, FileInfo.class, "metadata", "userMetadata", "thMetadata", "thUserMetadata", "attr", "hashInfo");

        // 这里手动获取数据库中的 json 字符串 并转成 元数据，方便使用
        info.setMetadata(jsonToMetadata(detail.getMetadata()));
        info.setUserMetadata(jsonToMetadata(detail.getUserMetadata()));
        info.setThMetadata(jsonToMetadata(detail.getThMetadata()));
        info.setThUserMetadata(jsonToMetadata(detail.getThUserMetadata()));
        // 这里手动获取数据库中的 json 字符串 并转成 附加属性字典，方便使用
        info.setAttr(jsonToDict(detail.getAttr()));
        // 这里手动获取数据库中的 json 字符串 并转成 哈希信息，方便使用
        info.setHashInfo(jsonToHashInfo(detail.getHashInfo()));
        return info;
    }

    /**
     * 将指定值转换成 json 字符串
     */
    private String valueToJson(Object value) {
        if (value == null) {
            return null;
        }
        return JsonUtils.toJson(value);
    }

    /**
     * 将 json 字符串转换成元数据对象
     */
    private Map<String, String> jsonToMetadata(String json) {
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return JsonUtils.toObj(json, new TypeReference<Map<String, String>>() {
        });
    }

    /**
     * 将 json 字符串转换成字典对象
     */
    private Dict jsonToDict(String json) {
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return JsonUtils.toObj(json, Dict.class);
    }

    /**
     * 将 json 字符串转换成哈希信息对象
     */
    private HashInfo jsonToHashInfo(String json) {
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return JsonUtils.toObj(json, HashInfo.class);
    }


    /**
     * 根据文件 ID 获取文件信息
     */
    public FileInfo getFileInfoById(String id) {
        FileDO fileDO = fileMapper.selectById(id);
        if (fileDO == null) {
            return null;
        }
        return toFileInfo(fileDO);
    }

    /**
     * 上传文件
     */
    public FileInfo uploadFile(FileUploadParams params, MultipartFile file) {

        return FileUploadUtils.uploadFile(params, file);
    }
}
