package cn.com.chinahitech.bjmarket.fileResources.mapper;

import cn.com.chinahitech.bjmarket.fileResources.entity.DocumentResources;
import cn.com.chinahitech.bjmarket.fileResources.entity.dayUpload;
import cn.com.chinahitech.bjmarket.fileResources.entity.documentResMenuRec;
import cn.com.chinahitech.bjmarket.fileResources.entity.sDocumentResources;
import cn.com.chinahitech.bjmarket.information.entity.MID;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 文档资源表 Mapper 接口
 * </p>
 *
 * @author zhufu
 * @since 2025-07-04
 */
@Mapper
public interface DocumentResourcesMapper extends BaseMapper<DocumentResources> {
    //管理员用类别模糊搜索+年份，专业，年级搜索
    List<DocumentResources> showlist(String primaryCategory,String secondaryCategory,String useyear,String applicableMajor,String applicableGrade);

    //设置资源状态
    @Update("UPDATE document_resources SET status = #{status} WHERE id = #{id}")
    int updatestatus(int status,int id);

    //重设资源路径
    @Update("UPDATE document_resources SET file_path = #{filePath} WHERE id = #{id}")
    int updatefilepath(String filePath,int id);

    //插入资源
    @Insert("INSERT INTO document_resources " +
            "(title, primary_category, secondary_category, useyear, view_count, download_count, " +
            "publish_time, file_path, author_id, author_name, status, applicable_major, applicable_grade) " +
            "VALUES (#{title}, #{primaryCategory}, #{secondaryCategory}, #{useyear}, #{viewCount}, #{downloadCount}, " +
            "#{publishTime}, #{filePath}, #{authorId}, #{authorName}, #{status}, #{applicableMajor}, #{applicableGrade})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertDocumentResource(DocumentResources resource);

    //学生用类别模糊搜索+年份，专业，年级搜索
    List<sDocumentResources> s_showlist(String primaryCategory, String secondaryCategory, String useyear, String applicableMajor, String applicableGrade);

    @Select("select * from document_resources where id=#{resid}")
    DocumentResources getRes(int resid);

    @Delete("DELETE from academic_journal where id=#{id}")
    int deleteById(int id);

    @Update("UPDATE document_resources SET view_count = view_count + 1 WHERE id = #{id}")
    int increaseView(int id);

    @Update("UPDATE document_resources SET download_count = download_count + 1 WHERE id = #{id}")
    int increaseDownload(int id);

    // 获取下载量和浏览量前10的学生笔记
    @Select("SELECT id, title, download_count" +
            "FROM document_resources " +
            "WHERE primary_category = '学生笔记' " +  // 添加一级分类限制条件
            "ORDER BY download_count DESC " +
            "LIMIT 10")
    List<documentResMenuRec> selectTop10StudentNotesByDownloadCount();

    @Select("SELECT id, title,view_count"+
            "FROM document_resources " +
            "WHERE primary_category = '学生笔记' " +  // 一级分类限制
            "ORDER BY view_count DESC " +  // 按浏览量降序排序
            "LIMIT 10")
    List<documentResMenuRec> selectTop10StudentNotesByViewCount();

    @Select("SELECT * FROM daily_resource_stats " +
            "WHERE stat_date >= CURDATE() - INTERVAL 7 DAY " +
            "ORDER BY stat_date DESC")
    List<dayUpload> recent7daySituation();
}
