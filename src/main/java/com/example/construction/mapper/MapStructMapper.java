package com.example.construction.mapper;

import com.example.construction.dto.ProjectDetailDto;
import com.example.construction.dto.ProjectDto;
import com.example.construction.models.Project;
import com.example.construction.request.ProjectRequestDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    //    //#region entity to dto
//    AmortizationDto amortizationToAmortizationDto(Amortization amortization);
//    ApplicationDto applicationToApplicationDto(Application application);
//    ConventionDto conventionToConventionDto(Convention convention);
//    DocumentDto documentToDocumentDto(Document document);
//    DocumentTypeDto documentTypeToDocumentDtoDocumentTypeDto(DocumentType documentType);
//    ObservationDto observationToObservationDto(Observation observation);
//    SupplierDto supplierToSupplierDto(Supplier supplier);
//    SupplierTypeDto supplierTypeToSupplierTypeDto(SupplierType supplierType);
//    ContributionInsuranceDTO contributionInsuranceToContributionInsuranceDTO(InsuranceContribution contributionInsurance);
//    ApplicationParameterDTO applicationParameterToApplicationParameterDTO(ApplicationParameter applicationParameter);
//    ApplicationTypeParameterDTO applicationTypeParameterToApplicationTypeParameterDTO(ApplicationTypeParameter applicationTypeParameter);
//    ApplicationRecoveryDTO applicationRecoveryToApplicationRecoveryDTO(ApplicationRecovery applicationRecovery);
//    ApportDto apportToApportDto(Apport apport);
//    ApplicationParameterHistoryDto applicationParameterHistoryToApplicationParameterHistoryDto(ApplicationParameterHistory applicationParameterHistory);
//    ApplicationRequestDto applicationToApplicationRequestDto(Application application);
//    ApplicationCommitmentsDto applicationCommitmentsToApplicationCommitmentsDto(ApplicationCommitments applicationCommitments);
//    InsuranceCompanyDto insuranceCompanyToInsuranceCompanyDto(InsuranceCompany insuranceCompany);
//    InsuranceSubscriptionDto insuranceSubscriptionToInsuranceSubscriptionDto(InsuranceSubscription insuranceSubscription);
//    InsuranceContributionDto insuranceContributionToInsuranceContributionDto(InsuranceContribution insuranceContribution);
//    InsuranceContributionPlanDto insuranceContributionPlanToInsuranceContributionPlanDto(InsuranceContributionPlan insuranceContributionPlan);
//    List<ApplicationDocumentDto> applicationDocumentToApplicationDocumentDto(List<ApplicationDocumentDto> applicationDocumentDtoList);
//    LoadHistoryDTO loadHistorytoLoadHistoryDTO(LoadHistory loadHistory);
//    LoadHistoryDetailDto loadHistoryDetailToLoadHistoryDetailDto(LoadHistoryDetail loadHistoryDetail);
//    LoadHistoryStatusDto loadHistoryStatusToLoadHistoryStatusDto(LoadHistoryStatus loadHistoryStatus);
//    CheckDto checkToCheckDto(Check check);
//    BankAccountDto bankAccountToBankAccountDto(BankAccount bankAccount);
//    BankDto bankToBankDto(Bank bank);
//    TiersDto tiersToTiersDto(Tiers tiers);
//    SiteTrackingDto siteTrackingToSiteTrackingDto(SiteTracking siteTracking);
//    SiteDocumentDto siteDocumentToSiteDocumentDto(SiteDocument siteDocument);
//    SiteDto siteToSiteDto(Site site);
//    ServiceTypeDto serviceTypeToServiceTypeDto(ServiceType serviceType);
//    ParcelDto parcelToParcelDto(Parcel parcel);
     ProjectDto projectToProjectDto(Project project);
     ProjectDetailDto projectToProjectDetailDto(Project project);

    //    CommercializationPhaseAmortizationDto commercializationPhaseAmortizationToCommercializationPhaseAmortizationDto(CommercializationPhaseAmortization commercializationPhaseAmortization);
//    ParcelConfigDto parcelConfigToParcelConfigDto(ParcelConfig parcelConfig);
//    PromoterRequestDto promoterToPromoterRequestDto(Promoter promoter);
//    PromoterDto promoterToPromoterDto(Promoter promoter);
//    SocietyDto societyToSocietyDto(Society society);
//    RepresentativeDto representativeToRepresentativeDto(Representative representative);
//    PersonPromoterDto personPromoterToPersonPromoterDto(Person person);
//    OfferDto offerToOfferDto(Offer offer);
//    OfferDetailDto offerToOfferDetailDto(Offer offer);
//    ProviderRequestDto providerToProviderRequestDto(Provider provider);
//    SiteDossierFeeDto siteDossierFeeToSiteDossierFeeDto(SiteDossierFee siteDossierFee);
//    DocumentSiteDto siteDocumentToSiteDossierFeeDto(SiteDocument siteDocument);
//    DocumentSiteDto offerDocumentToOfferDocumentDto(OfferDocument offerDocument);
//    ServiceOfferDetailDto serviceOfferToServiceOfferDetailDto(ServiceOffre serviceOffre);
//    PersonProviderDto personTOPersonProviderDto(Person person);
//    NotaryDto notaryToNotaryDto(Notary notary);
//    PaiementServiceOffreDto paiementServiceOffreToPaiementServiceOffreDto(PaiementServiceOffre paiementServiceOffre);
//    PaiementServiceOffreDocumentDto paiementServiceOffreDocumentToPaiementServiceOffreDocumentDto(PaymentDocument paymentDocument);
//    ServiceOffreContratDto serviceOffreContratDto(ServiceOffreContrat serviceOffreContrat);
//
//    ReimbursementDto reimbursementToReimbursementDto(Reimbursement reimbursement);
//    ReimbursementTrackingDto reimbursementTrackingToReimbursementTrackingDto(ReimbursementTracking reimbursementTracking);
//    TrackingAmortizationReservationDto trackingAmortizationReservationToTrackingAmortizationReservationDto(TrackingAmortizationReservation trackingAmortizationReservation);
//    TrackingCommercializationPhasePaymentDto trackingCommercializationPhasePaymentToTrackingCommercializationPhasePaymentDto(TrackingCommercializationPhasePayment trackingCommercializationPhasePayment);
//    TrackingPaymentComptantDto trackingPaymentComptantToTrackingPaymentComptantDto(TrackingPaymentComptant trackingPaymentComptant);
//    TrackingPaymentNotaryDto trackingPaymentNotaryToTrackingPaymentNotaryDto(TrackingPaymentNotary trackingPaymentNotary);
//
//    FuneralExpensesRequestDto funeralExpensesRequestToFuneralExpensesRequestDto(FuneralExpensesRequest funeralExpensesRequest);
//    FuneralExpensesResponseDto funeralExpensesResponseToFuneralExpensesResponseDto(FuneralExpensesRequest funeralExpensesRequest);
//    FuneralExpensesTrackingDto funeralExpensesTrackingToFuneralExpensesTrackingDto(FuneralExpensesTracking funeralExpensesTracking);
//    DeathInformationDto deathInformationToDeathInformationDto(DeathInformation deathInformation);
//    DeathInformationResponseDto deathInformationResponseDtoToDeathInformationResponseDto(DeathInformation deathInformation);
//    FuneralExpensesDocumentDto funeralExpensesDocumentToFuneralExpensesDocumentDto(FuneralExpensesDocument funeralExpensesDocument);
//    ProcurataireDto procurataireToProcurataireDto(Procurataire procurataire);
//    RetirementIndemnityApplicationDto retirementIndemnityApplicationToRetirementIndemnityApplicationDto(RetirementIndemnityApplication retirementIndemnityApplication);
//    RetirementDeathInformationDto retirementDeathInformationToRetirementDeathInformationDto(RetirementDeathInformation retirementDeathInformation);
//    RetirementIndemnityApplicationDocumentDto retirementIndemnityApplicationDocumentToRetirementIndemnityApplicationDocumentDto(RetirementIndemnityApplicationDocument retirementIndemnityApplicationDocument);
//    FondsSolidariteIdrConfigDto fondsSolidariteIdrConfigToFondsSolidariteIdrConfigDto(FondsSolidariteIdrConfig fondsSolidariteIdrConfig);
//    RetirementIndemnityApplicationTrackingDto retirementIndemnityApplicationTrackingToRetirementIndemnityApplicationTrackingDto(RetirementIndemnityApplicationTracking retirementIndemnityApplicationTracking);
//    // #endregion
//    // #region dto to entity
//
//    Amortization amortizationDtoToAmortization(AmortizationDto amortizationDto);
//    Application applicationDtoApplication(ApplicationDto applicationDto);
//    Application applicationEnrollDtoApplication(ApplicationEnrollDto applicationDto);
//    Convention conventionDtoToConvention(ConventionDto conventionDto);
//    Contract contractToContract(ContractDto contractDto);
//    ContractDto contractDtoToContract(Contract contract);
//    DocumentType documentTypeDtoToDocumentType(DocumentTypeDto documentTypeDto);
//    Observation observationDtoToObservation(ObservationDto observationDto);
//    Supplier supplierDtoToSupplier(SupplierDto supplierDto);
//    SupplierType supplierTypeDtoTOSupplierType(SupplierTypeDto supplierTypeDto);
//    PaymentDto paymentToPaymentDto(Payment payment);
//    Payment paymentDtoToPayment(PaymentDto paymentDto);
//    InsuranceContribution contributionInsuranceDTOToContributionInsurance(ContributionInsuranceDTO contributionInsuranceDTO);
//    ApplicationParameter applicationParameterDTOToApplicationParameter(ApplicationParameterDTO applicationParameterDTO);
//    ApplicationTypeParameter applicationTypeParameterDTOToApplicationTypeParameter(ApplicationTypeParameterDTO applicationTypeParameterDTO);
//    ApplicationRecovery applicationRecoveryDTOToApplicationRecovery(ApplicationRecoveryDTO applicationRecoveryDTO);
//    InsuranceSubscription subscriptionInsuranceDTOTosubscriptionInsurance(SubscriptionInsuranceDTO subscriptionInsuranceDTO);
//    SubscriptionInsuranceDTO subscriptionInsuranceTosubscriptionInsuranceDTO(SubscriptionInsuranceDTO subscriptionInsuranceDTO);
//    SubscriptionInsuranceDTO subscriptionInsuranceTosubscriptionInsuranceDTOs(InsuranceSubscription subscriptionInsurance);
//    InsuranceCompany insuranceCompanyDtoToInsuranceCompany(InsuranceCompanyDto insuranceCompany);
//    InsuranceSubscription insuranceSubscriptionDtoToInsuranceSubscription(InsuranceSubscriptionDto insuranceSubscription);
//    InsuranceContribution insuranceContributionDtoToInsuranceContribution(InsuranceContributionDto insuranceContribution);
//    InsuranceContributionPlan insuranceContributionPlanDtoToInsuranceContributionPlan(InsuranceContributionPlanDto insuranceContributionPlan);
//    ApplicationDtos applicationToApplicationDtos(Application applications);
//    ApplicationDataDTO applicationToApplicationDataDTO(Application application);
//    Apport apportDtoToApport(ApportDto apportDto);
//    ApplicationParameterHistory applicationParameterHistoryDtoToApplicationParameterHistory(ApplicationParameterHistoryDto applicationParameterHistory);
//    Application applicationRequestDtoToApplication(ApplicationRequestDto applicationRequestDto);
//    ApplicationCommitments applicationCommitmentsDtoToApplicationCommitments(ApplicationCommitmentsDto applicationCommitmentsDto);
//    LoadHistoryDetail loadHistoryDetailDtoToLoadHistoryDetail(LoadHistoryDetailDto loadHistoryDetailDto);
//    LoadHistoryStatus loadHistoryStatusDtoToLoadHistoryStatus(LoadHistoryStatusDto loadHistoryStatusDto);
//    Check checkDtoToCheck(CheckDto checkDto);
//    BankAccount bankAccountDtoToBankAccount(BankAccountDto bankAccountDto);
//    Bank bankDtoToBank(BankDto bankDto);
      Project ProjectDtoToProject(ProjectRequestDto projectDto) ;
       Project ProjectDetailDtoToProject(ProjectDetailDto projectDto) ;
//    Tiers tiersDtoToTiers(TiersDto tiersDto);
//    SiteTracking siteTrackingDtoToSiteTracking(SiteTrackingDto siteTrackingDto);
//    SiteDocument siteDocumentDtoToSiteDocument(SiteDocumentDto siteDocumentDto);
//    Site siteDtoToSite(SiteDto siteDto);
//    ServiceType serviceTypeDtoToServiceType(ServiceTypeDto serviceTypeDto);
//    Parcel parcelDtoToParcel(ParcelDto parcelDto);
//    Offer offerDtoToOffer(OfferDto offerDto);
//    Offer offerDetailDtoToOffer(OfferDetailDto offerDto);
//    Provider providerDtoToProvider(ProviderRequestDto providerRequestDto);
//    Promoter promoterRequestDtoToPromoter(PromoterRequestDto promoterRequestDto);
//    Promoter promoterDtoToPromoter(PromoterDto promoterDto);
//    Society societyDtoToSociety(SocietyDto societyDto);
//    Representative representativeDtoToRepresentative(RepresentativeDto representativeDto);
//    CommercializationPhaseAmortization commercializationPhaseAmortizationDtoToCommercializationPhaseAmortization(CommercializationPhaseAmortizationDto commercializationPhaseAmortizationDto);
//    ParcelConfig parcelConfigDtoToParcelConfig(ParcelConfigDto parcelConfig);
//    SiteDossierFee SiteDossierFeeDtoToSiteDossierFee(SiteDossierFeeDto siteDossierFeeDto);
//    ViabilizationPhaseSubscription ViabilizationPhaseSubscriptionDtoToViabilizationPhaseSubscription(ViabilizationPhaseSubscriptionDto viabilizationPhaseSubscriptionDto);
//    Notary notaryDtoToNotary(NotaryDto notaryDto);
//
//    Reimbursement reimbursementDtoToReimbursement(ReimbursementDto reimbursement);
//    ReimbursementTracking reimbursementTrackingDtoToReimbursementTracking(ReimbursementTrackingDto reimbursementTracking);
//
//    TrackingAmortizationReservation trackingAmortizationReservationDtoToTrackingAmortizationReservation(TrackingAmortizationReservationDto trackingAmortizationReservation);
//    TrackingCommercializationPhasePayment trackingCommercializationPhasePaymentDtoToTrackingCommercializationPhasePayment(TrackingCommercializationPhasePaymentDto trackingCommercializationPhasePayment);
//    TrackingPaymentComptant trackingPaymentComptantDtoToTrackingPaymentComptant(TrackingPaymentComptantDto trackingPaymentComptant);
//    TrackingPaymentNotary trackingPaymentNotaryDtoToTrackingPaymentNotary(TrackingPaymentNotaryDto trackingPaymentNotary);
//    RetirementIndemnityApplication retirementIndemnityApplicationDtoToRetirementIndemnityApplication(RetirementIndemnityApplicationDto retirementIndemnityApplication);
//    RetirementDeathInformation retirementDeathInformationDtoToRetirementDeathInformation(RetirementDeathInformationDto retirementDeathInformation);
//    RetirementIndemnityApplicationDocument retirementIndemnityApplicationDocumentDtoToRetirementIndemnityApplicationDocument(RetirementIndemnityApplicationDocumentDto retirementIndemnityApplicationDocument);
//    FondsSolidariteIdrConfig fondsSolidariteIdrConfigDtoToFondsSolidariteIdrConfig(FondsSolidariteIdrConfigDto fondsSolidariteIdrConfig);
//    RetirementIndemnityApplicationTracking retirementIndemnityApplicationTrackingDtoToRetirementIndemnityApplicationTracking(RetirementIndemnityApplicationTrackingDto retirementIndemnityApplicationTracking);
//
//
//    // #endregion
//
}
