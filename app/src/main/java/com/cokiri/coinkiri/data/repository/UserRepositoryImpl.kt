package com.cokiri.coinkiri.data.repository

import android.util.Log
import com.cokiri.coinkiri.data.remote.PreferencesManager
import com.cokiri.coinkiri.data.remote.api.AuthApi
import com.cokiri.coinkiri.data.remote.model.MemberInfo
import com.cokiri.coinkiri.data.remote.model.SignUpRequest
import com.cokiri.coinkiri.domain.repository.UserRepository
import javax.inject.Inject

/**
 * 회원가입을 위한 UserRepository 구현체
 */
class UserRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val preferencesManager: PreferencesManager
) : UserRepository {

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }

    override suspend fun signUpUser(accessToken: String, socialType: String): Result<Unit> {
        return try {
            val response = authApi.signUpUser(SignUpRequest(accessToken, socialType))

            val accessToken = response.result.accessToken       // 액세스 토큰
            val refreshToken = response.result.refreshToken     // 리프레시 토큰

            preferencesManager.saveAccessToken(accessToken)     // 액세스 토큰 저장
            preferencesManager.saveRefreshToken(refreshToken)   // 리프레시 토큰 저장

            Log.i(
                TAG, "회원가입 성공, " +
                        "accessToken : $accessToken, " +
                        "refreshToken : $refreshToken "
            )
            getMemberInfo()?.let { member ->
                Log.d(TAG, "사용자 정보: $member")
            }

            Result.success(Unit)

        } catch (error: Exception) {
            Log.e(TAG, "회원가입 실패", error)
            Result.failure(error)
        }
    }

    override suspend fun logoutUser(): Result<Unit> {
        return try {
            val accessToken = preferencesManager.getAccessToken()
            if (accessToken.isNullOrEmpty()) {

                // 토큰이 없을 경우 로그아웃 처리를 하지 않음
                Log.e(TAG, "로그아웃 실패: 토큰이 없음")
                Result.failure(Exception("토큰이 없음"))
            } else {
                Log.d(TAG, "로그아웃 요청 토큰: $accessToken")
                authApi.logoutUser("Bearer $accessToken")
                preferencesManager.clearTokens()
                Log.i(TAG, "로그아웃 성공")
                Result.success(Unit)
            }
        } catch (error: Exception) {
            Log.e(TAG, "로그아웃 실패", error)
            Result.failure(error)
        }
    }


    private suspend fun getMemberInfo(): MemberInfo? {
        return try {
            val accessToken = preferencesManager.getAccessToken()
            if (accessToken.isNullOrEmpty()) {
                Log.e(TAG, "토큰이 없어 사용자 정보를 가져올 수 없습니다.")
                null
            } else {
                val response = authApi.getMemberInfo("Bearer $accessToken")
                if (response.code == "OK") {
                    // 응답이 성공적으로 받아졌을 때, 결과를 UserInfo 객체로 변환하여 반환합니다.
                    val result = response.result
                    MemberInfo(
                        id = result.id,
                        nickname = result.nickname,
                        exp = result.exp,
                        level = result.level,
                        mileage = result.mileage,
                        pic = result.pic
                    )
                } else {
                    Log.e(TAG, "서버 응답 오류: ${response.message}")
                    null
                }
            }
        } catch (error: Exception) {
            Log.e(TAG, "사용자 정보 가져오기 실패", error)
            null
        }
    }


    override fun isLoggedIn(): Boolean {
        return !preferencesManager.getAccessToken().isNullOrEmpty()
    }
}