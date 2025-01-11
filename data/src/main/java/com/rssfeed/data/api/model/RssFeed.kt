package com.rssfeed.data.api.model

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("rss")
data class RssFeed(
  @XmlElement(true)
  val channel: Channel? = null,
)
