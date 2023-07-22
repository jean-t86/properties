package me.tadebois.properties.api

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResponse(
    @SerializedName("data") val data: List<Property>
)

@Keep
data class Property(
    @SerializedName("id") val id: String,
    @SerializedName("auction_date") val auctionDate: String,
    @SerializedName("available_from") val availableFrom: String,
    @SerializedName("bedrooms") val bedrooms: Int,
    @SerializedName("bathrooms") val bathrooms: Int,
    @SerializedName("carspaces") val carspaces: Int,
    @SerializedName("date_first_listed") val dateFirstListed: String,
    @SerializedName("date_updated") val dateUpdated: String,
    @SerializedName("description") val description: String,
    @SerializedName("display_price") val displayPrice: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("location") val location: Location,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("property_images") val propertyImages: List<PropertyImage>,
    @SerializedName("agent") val agent: Agent,
    @SerializedName("property_type") val propertyType: String,
    @SerializedName("sale_type") val saleType: String
)

@Keep
data class Location(
    @SerializedName("address") val address: String,
    @SerializedName("state") val state: String,
    @SerializedName("suburb") val suburb: String,
    @SerializedName("postcode") val postcode: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)

@Keep
data class Owner(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("dob") val dob: String,
    @SerializedName("avatar") val avatar: Avatar
)

@Keep
data class Avatar(
    @SerializedName("small") val small: AvatarImage,
    @SerializedName("medium") val medium: AvatarImage,
    @SerializedName("large") val large: AvatarImage
)

@Keep
data class AvatarImage(
    @SerializedName("url") val url: String
)

@Keep
data class PropertyImage(
    @SerializedName("id") val id: Int,
    @SerializedName("attachment") val attachment: ImageAttachment
)

@Keep
data class ImageAttachment(
    @SerializedName("url") val url: String,
    @SerializedName("thumb") val thumb: ImageSize,
    @SerializedName("medium") val medium: ImageSize,
    @SerializedName("large") val large: ImageSize
)

@Keep
data class ImageSize(
    @SerializedName("url") val url: String
)

@Keep
data class Agent(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("avatar") val avatar: Avatar
)
