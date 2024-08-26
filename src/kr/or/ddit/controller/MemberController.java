package kr.or.ddit.controller;

import kr.or.ddit.service.IMemberService;
import kr.or.ddit.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MemberController {
    private IMemberService service;
    private Scanner scan;
//    service =MemberServiceImpl(); // 서비스객체가 저장될 변수 선언
    
    public MemberController() {
        scan = new Scanner(System.in);
        service = MemberServiceImpl.getInstance();
    }
    public static void main(String[] args) {
        new MemberController().startMember();
    }

    private int displayMenu() {

        System.out.println("== 작업 선택 ==");
        System.out.println("1. 자료 추가");
        System.out.println("2. 자료 삭제");
        System.out.println("3. 자료 수정");
        System.out.println("4. 전체 자료 출력");
        System.out.println("5. 일부 자료 수정");
        System.out.println("0. 작업 끝");
        System.out.println();
        System.out.print("수행할 작업 번호 : ");

        return scan.nextInt();
    }

    public void startMember() {
        while (true) {
            int choice = displayMenu();
            switch (choice) {
                case 1:
                    insertMember();
                    break;
                case 2:
                    deleteMember();
                    break;
                case 3:
                    updateMember();
                    break;
                case 4:
                    displayAllMember();
                    break;
                case 5:
                    updateMemberV2();
                    break;
                case 0:
                    System.out.println("작업을 마칩니다");
                    return;
                default:
                    System.out.println("잘못입력했습니다");

            }
        }
    }

    private void updateMemberV2() {
        System.out.println("회원 수정 페이지입니다");

        System.out.print("수정하려는 회원 ID를 입력하시오: ");
        scan.nextLine();
        String memId = scan.nextLine();  // 여기에서 memId를 받아옵니다.



        int count = service.getMemberIdCount(memId);

        if (count == 0) {
            System.out.println("없는 회원 ID입니다");
            System.out.println("수정 작업을 마칩니다");
            return;
        }

        int num = 0;
        String updateField = null; // 변경할 컬럼명이 저장될 변수
        String titleMsg = null; // 변경할 값을 입력받을때 나타나는 메세지가 저장될 변수

        do {
            System.out.println();
            System.out.println("수정할 항목을 선택하세요..");
            System.out.println("1.비밀번호 2.회원이름 3.전화번호 4.회원주소");
            System.out.println("------------------------------");
            System.out.print("수정할 항목 선택 : ");
            num = scan.nextInt();

            switch (num) {
                case 1:
                    updateField = "mem_pass"; titleMsg = "비밀번호"; break;
                case 2:
                    updateField = "mem_name"; titleMsg = "회원이름"; break;
                case 3:
                    updateField = "mem_tel"; titleMsg = "전화번호"; break;
                case 4:
                    updateField = "mem_addr"; titleMsg = "회원주소"; break;
                default:
                    System.out.println("수정할 항목 선택이 잘못됐습니다 다시선택해주세요");

            }
        } while (num < 1 || num > 4);

        scan.nextLine(); // 이전에 남은 개행문자 제거
        System.out.println();
        System.out.print("새로운 " + titleMsg + " 입력 : ");
        String updateData = scan.nextLine();

        // 수정에 필요한 정보들을 Map에 추가한다
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MEMID", memId);
        paramMap.put("FIELD", updateField);
        paramMap.put("DATA", updateData);

        int cnt = service.updateMemberV2(paramMap);

        if (cnt > 0) {
            System.out.println("수정 성공");
        } else {
            System.out.println("수정 실패");
        }
    }

    private void displayAllMember() {
        System.out.println();

        List<MemberVO> memList = service.getAllMember();

        if (memList == null || memList.size() == 0) {
            System.out.println("등록된 회원이 없습니다");
        } else {
            for (MemberVO mem : memList) {
                System.out.println(mem.getMem_id() + ", " + mem.getMem_pass() + ", " +
                                    mem.getMem_name() + ", " + mem.getMem_tel()
                                    + ", " + mem.getMem_addr());
            }
        }

    }

    private void updateMember() {
        System.out.println();
        System.out.println("수정할 회원 정보를 입력하세요");
        scan.nextLine();
        System.out.print("회원 정보 입력 : ");
        String memId = scan.nextLine();

        int cnt = service.getMemberIdCount(memId);
        if (cnt == 0) {
            System.out.println("없는 정보입니다");
            return;
        }

        System.out.print("pw 입력");
        String memPass = scan.nextLine();
        System.out.println();

        System.out.print("name 입력");
        String name = scan.nextLine();
        System.out.println();

        System.out.print("tel 입력");
        String tel = scan.nextLine();
        System.out.println();

        System.out.print("address 입력");
        String addr = scan.nextLine();

        MemberVO memVo = new MemberVO();
        memVo.setMem_id(memId);
        memVo.setMem_pass(memPass);
        memVo.setMem_name(name);
        memVo.setMem_tel(tel);
        memVo.setMem_addr(addr);

        int result = service.updateMember(memVo);
        if (result > 0) {
            System.out.println("수정 성공");

        } else {
            System.out.println("수정 실패");
        }
    }

    private void deleteMember() {
        System.out.println();
        System.out.println("삭제할 회원 정보를 입력하세요");
        System.out.print("회원 정보 입력 : ");
        scan.nextLine();
        String memId = scan.nextLine();

        int cnt = service.deleteMember(memId);
        if (cnt > 0) {
            System.out.println("삭제 성공");
        } else {
            System.out.println("삭제 실패");
        }
    }


    //insert메소드
    private void insertMember() {
        System.out.println();
        System.out.println("추가할 회원 정보를 입력하세요");

        String memId = null; // 회원ID가 저장될변수
        int count = 0;
        do {

            System.out.print("회원 ID 입력 : ");
            memId = scan.next();

            count = service.getMemberIdCount(memId);

            if (count > 0) {
                System.out.println(memId + "는(은) 이미 등록된 회원 ID입니다.");
                System.out.println("다른 회원 ID를 입력하세요");
                System.out.println();
            }
        } while (count > 0);

        System.out.println();
        scan.nextLine();
        System.out.print("pw 입력");
        String pass = scan.nextLine();
        System.out.println();

        System.out.print("name 입력");
        String name = scan.nextLine();
        System.out.println();

        System.out.print("tel 입력");
        String tel = scan.nextLine();
        System.out.println();

        System.out.print("address 입력");
        String addr = scan.nextLine();
        System.out.println();

        // 입력받은 데이터들을 MemberVO객체에 저장한다
        MemberVO memVo = new MemberVO();
        memVo.setMem_id(memId);
        memVo.setMem_pass(pass);
        memVo.setMem_name(name);
        memVo.setMem_tel(tel);
        memVo.setMem_addr(addr);

        // 서비스 호출
        int cnt = service.insertMember(memVo);
        if (cnt > 0) {
            System.out.println("회원정보 등록 성공");
        } else {
            System.out.println("회원정보 등록 실패");
        }
    }
}
