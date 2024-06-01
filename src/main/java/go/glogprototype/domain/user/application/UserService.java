package go.glogprototype.domain.user.application;

import go.glogprototype.domain.user.dao.UserRepository;
import go.glogprototype.domain.user.domain.Authority;
import go.glogprototype.domain.user.domain.Member;
import go.glogprototype.domain.user.dto.UserDto.*;
import go.glogprototype.domain.user.dto.UserEditDto;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickName(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        Member user = Member.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickName(userSignUpDto.getNickname())
                .age(userSignUpDto.getAge())
                .city(userSignUpDto.getCity())
                .authority(Authority.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    public List<UserSignUpDto> findAll() {
        List<Member> all = userRepository.findAll( );
        List<UserSignUpDto> userSignUpDtos = new ArrayList<>(  );
        for(Member user : all) {
            UserSignUpDto userSignUpDto = new UserSignUpDto(user.getEmail(),user.getPassword(),user.getNickName(),user.getAge(),user.getCity());
            userSignUpDtos.add(userSignUpDto);
        }
        return userSignUpDtos;
    }


    @Transactional(readOnly = true)
    public UserEditDto getMemberEditDTO(String email) {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        UserEditDto memberEditDTO = new UserEditDto();
        memberEditDTO.setNickName(member.getNickName());
        memberEditDTO.setBio(member.getBio());
        memberEditDTO.setProfileImage(member.getImageUrl());
        memberEditDTO.setMbti(member.getMbti());
        memberEditDTO.setAge(member.getAge());
        memberEditDTO.setGender(member.getGender());
        memberEditDTO.setPhoneNumber(member.getPhoneNumber());

        return memberEditDTO;
    }

    @Transactional
    public void editMember(String email, UserEditDto memberEditDTO) {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        member.setNickName(memberEditDTO.getNickName());
        member.setBio(memberEditDTO.getBio());
        member.setImageUrl(memberEditDTO.getProfileImage());
        member.setMbti(memberEditDTO.getMbti().toUpperCase());
        member.setAge(memberEditDTO.getAge());
        member.setGender(memberEditDTO.getGender());
        member.setPhoneNumber(memberEditDTO.getPhoneNumber());

        // 변경사항 저장
        userRepository.save(member);
    }

     public UserInfoDto showUserInfo(Long memberId) {

        Optional<Member> findMember = userRepository.findById(memberId);


          return  new UserInfoDto( findMember.orElseThrow().getId(),findMember.orElseThrow().getName(),findMember.orElseThrow().getBio(),findMember.orElseThrow().getImageUrl() );
//         UserInfoDto.builder( )
//                .bio(findMember.orElseThrow().getBio())
//                .id(findMember.orElseThrow().getId())
//                .name(findMember.orElseThrow().getName())
//                .imageUrl(findMember.orElseThrow().getImageUrl()).build( );


    }

    public UserInfoDto updateUserInfo(Long memberId, UserInfoDto userInfoDto) {

        Optional<Member> findMember = userRepository.findById(memberId);
        findMember.orElseThrow().update(userInfoDto);

        return userInfoDto;
    }
}


