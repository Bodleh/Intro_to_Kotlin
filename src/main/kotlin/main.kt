import builder.SrvBuilder
import dto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import srv.UserSrv
import java.time.LocalDateTime
import java.util.Objects.toString

fun main(){
    val srv = SrvBuilder.buildService(UserSrv::class.java)
    val usersCall = srv.getUsers()

    usersCall.enqueue(object: Callback<Users> {
        override fun onResponse(call: Call<Users>, response: Response<Users>) {
            if (response.isSuccessful) {
                val userArr = response.body()!!
                userArr.data.forEach {
                    println("user: $it")
                }
            } else {
                println("reason: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<Users>, t: Throwable) {
            println("ERROR: $t")
        }
    })

    println("finish")

    val userCall = srv.getUser(1)
    userCall.enqueue(object: Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            val user = response.body()!!
            println("userSingle: ${user.data}")
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            println("ERROR:$t")
        }
    })

    val createUser = srv.createUser(CreateUserReq(
        name = "ivan",
        job = "student",
        current = toString(LocalDateTime.now())
    ))
    createUser.enqueue(object: Callback<CreateUserResp> {
        override fun onResponse(call: Call<CreateUserResp>, response: Response<CreateUserResp>){
            if (response.isSuccessful) {
                val body = response.body()!!
                println("Resp: $body")
            } else {
                println("reason: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<CreateUserResp>, t: Throwable) {
            println("ERROR: $t")
        }
    })
}