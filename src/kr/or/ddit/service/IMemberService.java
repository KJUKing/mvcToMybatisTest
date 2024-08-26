package kr.or.ddit.service;

import kr.or.ddit.vo.MemberVO;

import java.util.List;
import java.util.Map;

public interface IMemberService {


    /**
     * MemberVo에 저장된 자료를 DB에 insert하는 메소드
     * @param memVo DB에 insert할 자료가 저장된 MemberVO객체
     * @return insert작업이 성공하면 1 실패하면 0 반환
     */
    public int insertMember(MemberVO memVo);

    /**
     * 회원ID를 매개변수로 받아서 해당 회원 정보를 삭제하는 메소드
     * @param memId 삭제할 회원ID
     * @return 작업성공 :1 작업실패 :0
     */
    public int deleteMember(String memId);

    /**
     * MemberVO자료들을 이용하여 회원정보를 수정하는 메소드
     * @param memVo update할 회원정보가 저장된 MemberVO객체
     * @return 작업성공 1 실패 0
     */
    public int updateMember(MemberVO memVo);

    /**
     * DB의 전체 회원 정보를 List에 담아서 반환하는 메소드
     *
     * @param
     * @return MemberVO객체가 저장된 List객체
     */

    public List<MemberVO> getAllMember();

    /**
     * 회원ID를 매개변수로 받아서 해당 회원ID의 개수를 반환하는 메소드
     * @param memId 검색할 회원ID
     * @return 검색된 회원ID의 개수
     */
    public int getMemberIdCount(String memId);


    public int updateMemberV2(Map<String, String> paramMap);
}
