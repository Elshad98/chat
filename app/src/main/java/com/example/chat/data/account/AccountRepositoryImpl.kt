package com.example.chat.data.account

import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.account.AccountRepository
import com.example.chat.domain.type.Either
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.None
import com.example.chat.domain.type.flatMap
import com.example.chat.domain.type.onNext
import java.util.Calendar

class AccountRepositoryImpl(
    private val accountRemote: AccountRemote,
    private val accountCache: AccountCache
) : AccountRepository {

    override fun login(email: String, password: String): Either<Failure, AccountEntity> {
        return accountCache.getToken()
            .flatMap { token -> accountRemote.login(email, password, token) }
            .onNext { account ->
                account.password = password
                accountCache.saveAccount(account)
            }
    }

    override fun logout(): Either<Failure, None> {
        return accountCache.logout()
    }

    override fun register(email: String, name: String, password: String): Either<Failure, None> {
        return accountCache
            .getToken()
            .flatMap { token ->
                accountRemote.register(
                    email,
                    name,
                    password,
                    token,
                    userDate = Calendar.getInstance().timeInMillis
                )
            }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        throw UnsupportedOperationException("Password recovery is not supported")
    }

    override fun getCurrentAccount(): Either<Failure, AccountEntity> {
        return accountCache.getCurrentAccount()
    }

    override fun updateAccountToken(token: String): Either<Failure, None> {
        accountCache.saveToken(token)

        return accountCache
            .getCurrentAccount()
            .flatMap { account -> accountRemote.updateToken(account.id, token, account.token) }
    }

    override fun updateAccountLastSeen(): Either<Failure, None> {
        throw UnsupportedOperationException("Updating last seen is not supported")
    }

    override fun editAccount(entity: AccountEntity): Either<Failure, AccountEntity> {
        return accountCache
            .getCurrentAccount()
            .flatMap {
                accountRemote.editUser(
                    entity.id,
                    entity.email,
                    entity.name,
                    entity.password,
                    entity.status,
                    entity.token,
                    entity.image
                )
            }
            .onNext { account ->
                entity.image = account.image
                accountCache.saveAccount(entity)
            }
    }
}