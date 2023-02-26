package dto

data class CreateUserResp(
    val name: String,
    val job: String,
    val id: Int,
    val current: String
)
