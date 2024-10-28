package pt.isel.pdm.chimp.domain.pagination

/**
 * Represents a pagination request.
 *
 * Page indexing starts at 1 when using this class.
 *
 * @param page the page number
 * @param size the page size
 * @param getCount whether to count the total number of elements
 */
data class PaginationRequest(
    val page: Int,
    val size: Int,
    val getCount: Boolean = true,
)
