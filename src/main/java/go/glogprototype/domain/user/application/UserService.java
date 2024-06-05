package go.glogprototype.domain.user.application;

import go.glogprototype.domain.user.dao.UserReadRepository;
import go.glogprototype.domain.user.dao.UserWriteRepository;
import go.glogprototype.domain.user.domain.Authority;
import go.glogprototype.domain.user.domain.Member;
import go.glogprototype.domain.user.dto.UserDto.*;
import go.glogprototype.domain.user.dto.UserEditDto;
import go.glogprototype.global.config.DataSourceContextHolder;
import go.glogprototype.global.config.DataSourceType;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReadRepository userReadRepository;
    private final UserWriteRepository userWriteRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);

        if (userReadRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userReadRepository.findByNickName(userSignUpDto.getNickname()).isPresent()) {
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
        userWriteRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<UserSignUpDto> findAll() {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        List<Member> all = userReadRepository.findAll();
        List<UserSignUpDto> userSignUpDtos = new ArrayList<>();
        for (Member user : all) {
            UserSignUpDto userSignUpDto = new UserSignUpDto(user.getEmail(), user.getPassword(), user.getNickName(), user.getAge(), user.getCity());
            userSignUpDtos.add(userSignUpDto);
        }
        return userSignUpDtos;
    }

    @Transactional(readOnly = true)
    public UserEditDto getMemberEditDTO(String email) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        Member member = userReadRepository.findByEmail(email)
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
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
        Member member = userReadRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        member.setNickName(memberEditDTO.getNickName());
        member.setBio(memberEditDTO.getBio());
        member.setImageUrl(memberEditDTO.getProfileImage());
        member.setMbti(memberEditDTO.getMbti().toUpperCase());
        member.setAge(memberEditDTO.getAge());
        member.setGender(memberEditDTO.getGender());
        member.setPhoneNumber(memberEditDTO.getPhoneNumber());

        userWriteRepository.save(member);
    }

    @Transactional(readOnly = true)
    public UserInfoDto showUserInfo(Long memberId) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        Optional<Member> findMember = userReadRepository.findById(memberId);

        return new UserInfoDto(findMember.orElseThrow().getName(), findMember.orElseThrow().getBio(), findMember.orElseThrow().getImageUrl());
    }

    @Transactional
    public UserInfoDto updateUserInfo(Long memberId, UserInfoDto userInfoDto) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
        Optional<Member> findMember = userReadRepository.findById(memberId);
        findMember.orElseThrow().update(userInfoDto);

        return userInfoDto;
    }
}
