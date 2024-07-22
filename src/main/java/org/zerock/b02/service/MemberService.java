package org.zerock.b02.service;

import org.zerock.b02.dto.MemberJoinDTO;

public interface MemberService {

  static class MidExistException extends Exception {}

  void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
}
