package com.yavuzbahceci.gitfetcher.api.main.data.converters

import com.yavuzbahceci.gitfetcher.api.main.data.RepositoryResponse
import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity
import com.yavuzbahceci.gitfetcher.util.converter.Converter
import com.yavuzbahceci.gitfetcher.util.converter.ConverterProvider

class RepositoryResponseConverter(
    override val source: RepositoryResponse

): Converter<RepositoryResponse, RepositoryEntity?> {
    override fun convert(): RepositoryEntity? {
        return try {
            RepositoryEntity(
                extractId(),
                extractOpenIssuesCount(),
                extractStargazerCount(),
                extractName(),
                extractOwnerName(),
                extractAvatarUrl(),
                extractIsStarred()
            )
        }catch (exception: Exception){
            null
        }
    }

    private fun extractIsStarred(): Boolean {
        TODO()
        // check id if is starred

    }

    private fun extractAvatarUrl(): String {
        return source.owner?.avatarUrl.orEmpty()
    }

    private fun extractOwnerName(): String {
        return source.owner?.ownerName.orEmpty()
    }

    private fun extractName(): String {
        return source.repositoryName.orEmpty()
    }

    private fun extractStargazerCount(): Int? {
        return source.starsCount
    }

    private fun extractOpenIssuesCount(): Int? {
        return source.openIssuesCount
    }

    private fun extractId(): Int? {
        return source.repositoryId
    }


}

interface RepositoryResponseConverterProvider : ConverterProvider<RepositoryResponse, RepositoryEntity?>

class RepositoryResponseConverterProviderImpl(

): RepositoryResponseConverterProvider {
    override fun provide(source: RepositoryResponse): Converter<RepositoryResponse, RepositoryEntity?> {
        return RepositoryResponseConverter(source)
    }
}