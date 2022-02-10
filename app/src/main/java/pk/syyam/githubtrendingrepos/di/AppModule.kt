package pk.syyam.githubtrendingrepos.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pk.syyam.githubtrendingrepos.network.RetrofitService
import pk.syyam.githubtrendingrepos.repository.DefaultGithubTrendingRepository
import pk.syyam.githubtrendingrepos.repository.GithubTrendingRepository
import pk.syyam.githubtrendingrepos.utils.PreferenceKeys
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): RetrofitService =
        Retrofit.Builder()
            .baseUrl(RetrofitService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)


    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        api: RetrofitService
    ) = DefaultGithubTrendingRepository(api) as GithubTrendingRepository


    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PreferenceKeys.KEY_PREF_NAME, Context.MODE_PRIVATE)
    }
}