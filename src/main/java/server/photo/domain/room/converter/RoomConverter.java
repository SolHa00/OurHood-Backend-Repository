package server.photo.domain.room.converter;

import server.photo.domain.invitation.dto.response.InvitationInfo;
import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.room.dto.request.RoomCreateRequest;
import server.photo.domain.room.dto.response.*;
import server.photo.domain.room.entity.Room;
import server.photo.domain.user.dto.response.MyInfo;
import server.photo.domain.user.dto.response.MyPageResponse;
import server.photo.domain.user.dto.response.RoomInfo;
import server.photo.domain.user.entity.User;

import java.util.List;

public class RoomConverter {

    public static Room toRoom(RoomCreateRequest request, User user) {
        return Room.builder()
                .userId(user.getId())
                .roomName(request.getRoomName())
                .roomDescription(request.getRoomDescription())
                .build();
    }

    public static RoomCreateResponse toRoomCreateResponse(Room room) {
        return RoomCreateResponse.builder()
                .roomId(room.getId())
                .build();
    }


    public static RoomListInfo toRoomListInfo(Room room, String hostName) {
        return RoomListInfo.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .hostName(hostName)
                .numOfMembers(room.getRoomMembers().size())
                .createdAt(room.getCreatedAt())
                .thumbnail(room.getThumbnailImage())
                .build();
    }

    public static RoomListResponse toRoomListResponse(List<RoomListInfo> roomListInfos) {
        return RoomListResponse.builder()
                .rooms(roomListInfos)
                .build();
    }

    public static MyPageResponse toMyPageResponse(MyInfo myInfo, List<RoomInfo> myRooms, List<InvitationInfo> invitations) {
        return MyPageResponse.builder()
                .myInfo(myInfo)
                .myRooms(myRooms)
                .invitations(invitations)
                .build();
    }

    public static RoomInvitationList toRoomInvitationList(Invitation invitation, User inviter) {
        return RoomInvitationList.builder()
                .invitationId(invitation.getId())
                .nickname(inviter.getNickname())
                .createdAt(invitation.getCreatedAt())
                .build();
    }

    public static RoomInvitationsDto toRoomInvitationsDto(List<RoomInvitationList> invitationLists) {
        return RoomInvitationsDto.builder()
                .invitaionList(invitationLists)
                .build();
    }

    public static JoinRequestDetail toJoinRequestDetail(JoinRequest joinRequest, User user) {
        return JoinRequestDetail.builder()
                .joinId(joinRequest.getId())
                .nickname(user.getNickname())
                .createdAt(joinRequest.getCreatedAt())
                .build();
    }

    public static JoinRequestListResponse toJoinRequestListResponse(List<JoinRequestDetail> joinList) {
        return JoinRequestListResponse.builder()
                .joinList(joinList)
                .build();
    }

    public static UserContextSuccess toUserContextSuccess(Boolean isMember) {
        return UserContextSuccess.builder()
                .isMember(isMember)
                .build();
    }

    public static RoomMetadataSuccess toRoomMetaDataSuccess(Room room, User host) {
        return RoomMetadataSuccess.builder()
                .roomId(room.getId())
                .userId(host.getId())
                .hostName(host.getNickname())
                .createdAt(room.getCreatedAt())
                .build();
    }

    public static RoomDetail toRoomDetail(Room room) {
        return RoomDetail.builder()
                .roomName(room.getRoomName())
                .roomDescription(room.getRoomDescription())
                .thumbnail(room.getThumbnailImage())
                .build();
    }

    public static RoomPrivate toRoomPrivate(List<MemberInfo> members, List<MomentInfo> moments, Long numOfNewJoinRequests) {
        return RoomPrivate.builder()
                .members(members)
                .moments(moments)
                .numOfNewJoinRequests(numOfNewJoinRequests)
                .build();
    }

    public static RoomEnterSuccessResponse toRoomEnterSuccessResponse(UserContextSuccess userContext, RoomMetadataSuccess roomMetadata, RoomDetail roomDetail, RoomPrivate roomPrivate) {
        return RoomEnterSuccessResponse.builder()
                .userContext(userContext)
                .roomMetaData(roomMetadata)
                .roomDetail(roomDetail)
                .roomPrivate(roomPrivate)
                .build();
    }

    public static UserContextFail toUserContextFail(Boolean isMember, Boolean isJoinRequestSent) {
        return UserContextFail.builder()
                .isMember(isMember)
                .isJoinRequestSent(isJoinRequestSent)
                .build();
    }

    public static RoomMetadataFail toRoomMetadataFail(Room room, User host) {
        return RoomMetadataFail.builder()
                .roomId(room.getId())
                .hostName(host.getNickname())
                .createdAt(room.getCreatedAt())
                .build();
    }

    public static RoomEnterFailResponse toRoomEnterFailResponse(UserContextFail userContext, RoomMetadataFail roomMetadata, RoomDetail roomDetail) {
        return RoomEnterFailResponse.builder()
                .userContext(userContext)
                .roomMetadata(roomMetadata)
                .roomDetail(roomDetail)
                .build();
    }
}