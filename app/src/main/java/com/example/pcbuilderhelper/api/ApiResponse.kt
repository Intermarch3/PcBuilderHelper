package com.example.pcbuilderhelper.api
import com.example.pcbuilderhelper.models.Component

data class Paging(
    val url_format: String?,
    val max: Int?,
    val first: Any?, // Peut-être spécifier un type plus précis si connu
    val previous: Any?, // Peut-être spécifier un type plus précis si connu
    val current: Int?,
    val next: String?,
    val last: String?
)

data class SalesInCategory(
    val has_sales: Boolean?,
    val url_category: Any?, // Peut-être spécifier un type plus précis si connu
    val label: Any? // Peut-être spécifier un type plus précis si connu
)

data class Breadcrumb(
    val label: Any?, // Peut-être spécifier un type plus précis si connu
    val url: String?
)

data class Attributes(
    val sticker: Any?, // Peut-être spécifier un type plus précis si connu
    val is_new: Int?,
    val is_destockage: Int?,
    val is_price_drop: Int?,
    val is_alert_availability: Int?,
    val date_sale_on_soon: Any?, // Peut-être spécifier un type plus précis si connu
    val time_end: Any? // Peut-être spécifier un type plus précis si connu
)

data class Offer(
    val availability: Availability,
    val shop_availability: ShopAvailability,
    val price: Price,
    val attributes: Attributes
)

data class Availability(
    val code: String?,
    val label: String?
)

data class ShopAvailability(
    val code: String?,
    val label: String?
)

data class Price(
    val is_discount: Int?,
    val type_promo: String?,
    val price_final: Double?,
    val ecotax: Double?
)

data class Reviews(
    val count_rating: Int?,
    val average_rating: Double?
)

data class Media(
    val main: Main
)

data class Main(
    val hash_id: String?,
    val width: Int?,
    val height: Int?,
    val transparent: Boolean?
)

data class Product(
    val ref: String?,
    val label: String?,
    val sublabel: String?,
    val offer: Offer?,
    val reviews: Reviews?,
    val availability: String?,
    val shop_availability: String?,
    val restocking_date: String?,
    val preorder_date: String?,
    val media: Media?,
    val url_product: String?
)

data class Metadata(
    val title: String?,
    val og_title: String?,
    val meta_description: String?,
    val og_description: String?,
    val canonical: String?,
    val og_url: String?,
    val og_image: String?,
    val robot: String?
)

data class Result(
    val paging: Paging?,
    val label_category: String?,
    val sales_in_category: SalesInCategory?,
    val is_sales_category: Boolean?,
    val breadcrumb: List<Breadcrumb>?,
    val metadata: Metadata?,
    val product_list: ProductList?
)

data class ApiResponse(
    val status: String?,
    val result: Result?
)

data class ProductList(
    val count_read: Int?,
    val count_write: Int?,
    val document: List<Product>?
)