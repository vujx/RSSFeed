package com.rssfeed.data.api.model

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("channel")
data class Channel(
  @XmlElement(true)
  val title: String? = null,
  @XmlElement(true)
  val description: String? = null,
  @XmlElement(true)
  val link: String? = null,
  @XmlElement(true)
  val image: Image? = null,
  @XmlElement(true)
  val lastBuildDate: String? = null,
  @XmlElement(true)
  val articles: List<Article> = emptyList(),
)

@Serializable
@XmlSerialName("image")
data class Image(
  @XmlElement(true)
  val url: String,
)
