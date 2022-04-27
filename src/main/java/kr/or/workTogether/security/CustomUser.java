package kr.or.workTogether.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.or.workTogether.common.vo.EmployeeAuthVO;
import kr.or.workTogether.common.vo.EmployeeVO;


public class CustomUser extends User {

	private EmployeeVO empVO;
		
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	//return member==null?null:new CustomUser(member);
	//사용자가 정의한 MemberVO 타입을 
	//스프링 시큐리티 UsersDetails 타입으로 변환
	public CustomUser(EmployeeVO empVO) {
		//super(username, password, authorities);
//		super(member.getMemberid(), member.getPassword(),
//				member.getAuthList().stream()
//				.map(auth->new SimpleGrantedAuthority(
//						auth.getAuth())).collect(Collectors.toList()));
		super(empVO.getUsername(), empVO.getPassword(), getList(empVO));
		
		this.empVO = empVO;		
	}
	
	public EmployeeVO getUser() {
		return this.empVO;
	}
	
	public static List<SimpleGrantedAuthority> getList(EmployeeVO empVO) {
		//cus : 1) VW_USER (1) 2) VW_USER_AUTH (N)
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		//authList : {"a001":"ROLE_MEMBER","a001":"ROLE_ADMIN"}
		List<EmployeeAuthVO> authList = empVO.getEmployeeAuthList();
		for(EmployeeAuthVO auth : authList) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(auth.getAuth());
			authorities.add(authority);
		}
		
		return authorities;
	}
	
	
	
}




