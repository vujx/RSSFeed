package com.rssfeed.data.api.model

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("channel")
data class Channel(
  @XmlElement(true)
  val title: String?,
  @XmlElement(true)
  val description: String?,
  @XmlElement(true)
  val link: String?,
  @XmlElement(true)
  val image: Image?,
  @XmlElement(true)
  val lastBuildDate: String?,
  @XmlElement(true)
  val articles: List<Article>,
)

@Serializable
@XmlSerialName("image")
data class Image(
  @XmlElement(true)
  val url: String?,
)
