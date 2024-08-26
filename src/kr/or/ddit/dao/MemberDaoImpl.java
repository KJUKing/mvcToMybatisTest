package kr.or.ddit.dao;

import kr.or.ddit.util.MybatisUtil;
import kr.or.ddit.vo.MemberVO;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberDaoImpl implements IMemberDao{

    private static MemberDaoImpl dao;
    SqlSession session = null;

    //2번 기본생성자 private생성

    private MemberDaoImpl() {
    }

    public static MemberDaoImpl getInstance() {
        if (dao == null) {
            dao = new MemberDaoImpl();
        }
        return dao;
    }

    @Override
    public int insertMember(MemberVO memVo) {
        int cnt = 0; //반환값이 저장될 변수
        try {
            session = MybatisUtil.getSqlSession();
            cnt = session.insert("myMember.insertMember", memVo);
            session.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return cnt;
    }

    @Override
    public int deleteMember(String memId) {
        int cnt = 0;
        try {
            session = MybatisUtil.getSqlSession();
            cnt = session.delete("myMember.deleteMember", memId);
            session.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            session.close();
        }
        return cnt;
    }

    @Override
    public int updateMember(MemberVO memVo) {
        int cnt = 0;

        try {
            session = MybatisUtil.getSqlSession();
            cnt = session.update("myMember.updateMember", memVo);
            session.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return cnt;
    }

    @Override
    public List<MemberVO> getAllMember() {
        List<MemberVO> memberList = null;

        try {
            session = MybatisUtil.getSqlSession();
             memberList = session.selectList("myMember.getAllMember");
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           session.close();
        }
        return memberList;
    }

    @Override
    public int getMemberIdCount(String memId) {
        int count = 0; // 반환값 변수
        try {
            session = MybatisUtil.getSqlSession();
            count = session.selectOne("myMember.getMemberIdCount", memId);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return count;
    }

    @Override
    public int updateMemberV2(Map<String, String> paramMap) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int cnt = 0;
        try {


            String sql = "update mymember set " + paramMap.get("FIELD") + " = ? where MEM_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paramMap.get("DATA"));
            pstmt.setString(2, paramMap.get("MEMID"));
            cnt = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            };
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            };
        }

        return cnt;
    }

}
