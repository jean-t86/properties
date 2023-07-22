package me.tadebois.properties.propertyapi

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResponse(
    @SerializedName("data") val data: List<Property>
)

@Keep
data class Property(
    @SerializedName("id") val id: String,
    @SerializedName("auction_date") val auction_date: String,
    @SerializedName("available_from") val available_from: String,
    @SerializedName("bedrooms") val bedrooms: Int,
    @SerializedName("bathrooms") val bathrooms: Int,
    @SerializedName("carspaces") val carspaces: Int,
    @SerializedName("date_first_listed") val date_first_listed: String,
    @SerializedName("date_updated") val date_updated: String,
    @SerializedName("description") val description: String,
    @SerializedName("display_price") val display_price: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("location") val location: Location,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("property_images") val property_images: List<PropertyImage>,
    @SerializedName("agent") val agent: Agent,
    @SerializedName("property_type") val property_type: String,
    @SerializedName("sale_type") val sale_type: String
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
    @SerializedName("first_name") val first_name: String,
    @SerializedName("last_name") val last_name: String,
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
    @SerializedName("first_name") val first_name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("company_name") val company_name: String,
    @SerializedName("avatar") val avatar: Avatar
)
