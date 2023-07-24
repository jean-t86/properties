package me.tadebois.properties.ui

import me.tadebois.properties.api.Agent
import me.tadebois.properties.api.Avatar
import me.tadebois.properties.api.AvatarImage
import me.tadebois.properties.api.ImageAttachment
import me.tadebois.properties.api.ImageSize
import me.tadebois.properties.api.Location
import me.tadebois.properties.api.Owner
import me.tadebois.properties.api.Property
import me.tadebois.properties.api.PropertyImage

object Helpers {
    fun getStreetAddress(property: Property): String? =
        property.location.address.split(",").getOrNull(0)?.trim()

    fun getSuburb(property: Property): String? =
        property.location.address.split(",").getOrNull(1)?.trim()

    fun formatPropertyType(property: Property): String =
        property.propertyType.lowercase().replaceFirstChar { it.titlecase() }

    fun formatAgentName(property: Property): String {
        val firstName = property.agent.firstName
        val lastName = property.agent.lastName
        return "$firstName $lastName"
    }

    fun getProperty(): Property = Property(
        id = "1",
        auctionDate = "2020-10-10T23:00:00+10:00",
        availableFrom = "2020-10-10T23:00:00+10:00",
        bedrooms = 6,
        bathrooms = 3,
        carspaces = 2,
        dateFirstListed = "2019-01-16T00:00:00+00:00",
        dateUpdated = "2020-09-02T00:00:00+00:00",
        description = "Designed by MHN Design Union with interiors by Burley Katon Halliday, 'The Beach House' captures the essence of Bondi's laidback lifestyle, whilst incorporating high-end finishes, house-like proportions and cutting-edge design throughout.",
        displayPrice = "$2,430,000",
        currency = "AUD",
        Location(
            address = "10/178 Campbell Parade, Bondi Beach NSW 2026",
            state = "NSW",
            suburb = "Bondi Beach",
            postcode = "2026",
            latitude = 33.8915,
            longitude = 151.2767
        ),
        owner = Owner(
            firstName = "Sally", lastName = "Saunders", dob = "1972-02-23T00:00:00+00:00", Avatar(
                small = AvatarImage("https://images.pexels.com/photos/1987301/pexels-photo-1987301.jpeg"),
                medium = AvatarImage("https://images.pexels.com/photos/1987301/pexels-photo-1987301.jpeg"),
                large = AvatarImage("https://images.pexels.com/photos/1987301/pexels-photo-1987301.jpeg")
            )
        ),
        propertyImages = listOf(
            PropertyImage(
                id = 12, attachment = ImageAttachment(
                    url = "https://cdn.pixabay.com/photo/2016/11/18/17/46/architecture-1836070__340.jpg",
                    thumb = ImageSize("https://cdn.pixabay.com/photo/2016/11/18/17/46/architecture-1836070__340.jpg"),
                    medium = ImageSize("https://cdn.pixabay.com/photo/2016/11/18/17/46/architecture-1836070__340.jpg"),
                    large = ImageSize("https://cdn.pixabay.com/photo/2016/11/18/17/46/architecture-1836070__340.jpg")
                )
            ), PropertyImage(
                id = 13, attachment = ImageAttachment(
                    url = "https://cdn.pixabay.com/photo/2016/11/29/03/53/architecture-1867187__340.jpg",
                    thumb = ImageSize("https://cdn.pixabay.com/photo/2016/11/29/03/53/architecture-1867187__340.jpg"),
                    medium = ImageSize("https://cdn.pixabay.com/photo/2016/11/29/03/53/architecture-1867187__340.jpg"),
                    large = ImageSize("https://cdn.pixabay.com/photo/2016/11/29/03/53/architecture-1867187__340.jpg")
                )
            )
        ),
        agent = Agent(
            firstName = "Earl",
            lastName = "Thomas",
            companyName = "Sydney Real Estate",
            avatar = Avatar(
                small = AvatarImage("https://images.pexels.com/photos/3556533/pexels-photo-3556533.jpeg"),
                medium = AvatarImage("https://images.pexels.com/photos/3556533/pexels-photo-3556533.jpeg"),
                large = AvatarImage("https://images.pexels.com/photos/3556533/pexels-photo-3556533.jpeg")
            )
        ),
        propertyType = "villa",
        saleType = "buy"
    )
}
